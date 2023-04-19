
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.entities.Activity;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------
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
		Activity activity;

		activityId = super.getRequest().getData("id", int.class);
		activity = this.repository.findOneActivityById(activityId);
		status = activity != null && !activity.getEnrolment().isDraftMode() && super.getRequest().getPrincipal().getAccountId() == activity.getEnrolment().getStudent().getUserAccount().getId();

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
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "indication", "startDate", "endDate", "link");

	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "student.activity.form.error.startDate");
		super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "endDate", "student.activity.form.error.endDate");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {

		assert object != null;

		//		SelectChoices choices;
		//		Tuple tuple;
		//
		//		choices = SelectChoices.from(ActivityType.class, object.getIndication());
		//
		//		tuple = super.unbind(object, "title", "abstract$", "indication", "startDate", "endDate", "link");
		//		tuple.put("indication", choices.getSelected().getKey());
		//		tuple.put("indications", choices);
		//
		//		super.getResponse().setData(tuple);

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(ActivityType.class, object.getIndication());

		tuple = super.unbind(object, "title", "abstract$", "indication", "startDate", "endDate", "link");
		tuple.put("indicationn", choices.getSelected().getKey());
		tuple.put("indications", choices);

		super.getResponse().setData(tuple);
	}

}
