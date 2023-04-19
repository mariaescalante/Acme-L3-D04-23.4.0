
package acme.features.assistant.session;

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
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
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

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime"))
			super.state(MomentHelper.isBefore(object.getStartTime(), object.getEndTime()), "startTime", "assistant.session.form.error.startTime");
		super.state(MomentHelper.isBefore(object.getStartTime(), object.getEndTime()), "endTime", "assistant.session.form.error.endTime");
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
