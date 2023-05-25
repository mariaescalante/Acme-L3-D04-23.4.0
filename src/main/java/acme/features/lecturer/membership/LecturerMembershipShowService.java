
package acme.features.lecturer.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Membership;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerMembershipShowService extends AbstractService<Lecturer, Membership> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerMembershipRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseByMembershipId(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Membership object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneMembershipById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Membership object) {
		assert object != null;
		final Tuple tuple;
		tuple = super.unbind(object, "title");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("lecture", object.getLecture().getTitle());

		super.getResponse().setData(tuple);
	}

}
