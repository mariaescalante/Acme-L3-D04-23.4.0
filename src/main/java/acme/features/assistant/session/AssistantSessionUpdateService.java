
package acme.features.assistant.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Session;
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

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "indication", "startTime", "endTime", "link");

		super.getResponse().setData(tuple);
	}

}
