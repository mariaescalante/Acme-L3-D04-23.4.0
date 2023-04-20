
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

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
		int masterId;
		Enrolment enrolment;
		masterId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(masterId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Activity> objects;
		int enrolmentId;
		enrolmentId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyActivitiesByEnrolmentId(enrolmentId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "indication");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Activity> objects) {
		assert objects != null;
		int enrolmentId;
		Enrolment enrolment;
		final boolean showCreate;
		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		showCreate = super.getRequest().getPrincipal().hasRole(enrolment.getStudent()) && !enrolment.isDraftMode();
		super.getResponse().setGlobal("masterId", enrolmentId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
