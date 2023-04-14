
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

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
		int enrolmentId;
		Enrolment enrolment;
		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;

		int enrolmentId;
		Enrolment enrolment;
		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		object = new Activity();
		object.setTitle("");
		object.setAbstract$("");
		object.setIndication(null);
		object.setStartDate(null);
		object.setEndDate(null);
		object.setLink("");
		object.setEnrolment(enrolment);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "indication", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(ActivityType.class, object.getIndication());

		tuple = super.unbind(object, "title", "abstract$", "startDate", "endDate", "link");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("indication", choices.getSelected().getKey());
		tuple.put("indications", choices);
		super.getResponse().setData(tuple);
	}

}
