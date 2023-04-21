
package acme.features.assistant.session;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SessionType;
import acme.entities.Session;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionUpdateService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		Session session;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(sessionId);
		status = session != null && session.getTutorial().isDraftMode() && super.getRequest().getPrincipal().getAccountId() == session.getTutorial().getAssistant().getUserAccount().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionById(id);

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
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SessionType.class, object.getIndication());
		tuple = super.unbind(object, "title", "abstract$", "startTime", "endTime", "link");
		tuple.put("indication", choices.getSelected().getKey());
		tuple.put("indications", choices);
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);
	}

}
