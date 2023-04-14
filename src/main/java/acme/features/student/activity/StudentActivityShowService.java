
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
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int activityId;
		Enrolment object;
		activityId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentByActivityId(activityId);
		status = object != null && super.getRequest().getPrincipal().hasRole(object.getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(ActivityType.class, object.getIndication());

		tuple = super.unbind(object, "title", "abstract$", "startDate", "endDate", "link");
		tuple.put("indication", choices.getSelected().getKey());
		tuple.put("indications", choices);
		tuple.put("masterId", object.getEnrolment().getId());

		super.getResponse().setData(tuple);
	}
}
