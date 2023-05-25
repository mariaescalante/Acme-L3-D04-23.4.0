
package acme.features.assistant.session;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SessionType;
import acme.entities.Session;
import acme.entities.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionCreateService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && tutorial.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session object;

		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);

		object = new Session();
		object.setTitle("");
		object.setAbstract$("");
		object.setIndication(SessionType.THEORY);
		object.setStartTime(null);
		object.setEndTime(null);
		object.setLink("");
		object.setTutorial(tutorial);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "indication", "startTime", "endTime", "link");
	}

	@Override
	public void validate(final Session object) {
		assert object != null;

		if (object.getStartTime() != null && object.getEndTime() != null) {
			//La fecha de inicio debe ser al menos un dia posterior a la fecha actual
			final LocalDateTime date3 = LocalDateTime.ofInstant(MomentHelper.getCurrentMoment().toInstant(), ZoneId.systemDefault());
			final LocalDateTime date4 = LocalDateTime.ofInstant(object.getStartTime().toInstant(), ZoneId.systemDefault());

			final int numeroDeDias = 1;
			final int convertidorDiasMinutos = numeroDeDias * 24 * 60;
			final long minutesBetween = ChronoUnit.MINUTES.between(date3, date4);

			if (!super.getBuffer().getErrors().hasErrors("startTime"))
				super.state(minutesBetween >= convertidorDiasMinutos, "startTime", "assistant.session.form.error.unDia");

			//La fecha de inicio debe ser anterior a la fecha de fin
			if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime")) {
				super.state(MomentHelper.isBefore(object.getStartTime(), object.getEndTime()), "startTime", "assistant.session.form.error.startTime");
				super.state(MomentHelper.isBefore(object.getStartTime(), object.getEndTime()), "endTime", "assistant.session.form.error.endTime");
			}
			//La sesion debe durar entre 1 y 5 horas
			final LocalDateTime date1 = LocalDateTime.ofInstant(object.getStartTime().toInstant(), ZoneId.systemDefault());
			final LocalDateTime date2 = LocalDateTime.ofInstant(object.getEndTime().toInstant(), ZoneId.systemDefault());

			final int numeroDeHoras1 = 1;
			final int numeroDeHoras2 = 5;
			final int convertidorHorasMinutos1 = numeroDeHoras1 * 60;
			final int convertidorHorasMinutos2 = numeroDeHoras2 * 60;
			final long minutesBetween1 = ChronoUnit.MINUTES.between(date1, date2);

			if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime")) {
				super.state(minutesBetween1 >= convertidorHorasMinutos1, "startTime", "assistant.session.form.error.duration");
				super.state(minutesBetween1 >= convertidorHorasMinutos1, "endTime", "assistant.session.form.error.duration");
				super.state(minutesBetween1 <= convertidorHorasMinutos2, "startTime", "assistant.session.form.error.duration");
				super.state(minutesBetween1 <= convertidorHorasMinutos2, "endTime", "assistant.session.form.error.duration");
			}
		} else
			super.state(true, "endDate", "administrator.offer.form.error.endDate");
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SessionType.class, object.getIndication());

		tuple = super.unbind(object, "title", "abstract$", "startTime", "endTime", "link");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("indication", choices.getSelected().getKey());
		tuple.put("indications", choices);
		super.getResponse().setData(tuple);
	}

}
