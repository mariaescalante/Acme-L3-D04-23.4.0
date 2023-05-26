
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService<Auditor, Audit> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit audit;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditById(masterId);
		auditor = audit == null ? null : audit.getAuditor();
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
		course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;

			existing = this.repository.findOneAuditByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "auditor.audit.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choices;

		courses = this.repository.findManyCourse();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");

		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("draftMode", object.isDraftMode());
		super.getResponse().setData(tuple);

	}

}
