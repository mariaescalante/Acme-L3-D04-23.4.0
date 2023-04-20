
package acme.features.assistant.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Session;
import acme.entities.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionDeleteService extends AbstractService<Assistant, Session> {

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
		Tutorial tutorial;

		sessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialBySesionId(sessionId);
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().getAccountId() == tutorial.getAssistant().getUserAccount().getId();

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
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "indication", "startTime", "endTime", "link");

		super.getResponse().setData(tuple);
	}

}
