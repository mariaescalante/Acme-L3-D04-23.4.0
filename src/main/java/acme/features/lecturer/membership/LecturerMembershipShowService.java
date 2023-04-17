
package acme.features.lecturer.membership;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.Membership;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
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

		super.getResponse().setAuthorised(true);
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
		Principal principal;
		final Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choices;
		Collection<Lecture> lectures;
		final SelectChoices choices2;

		principal = super.getRequest().getPrincipal();
		courses = this.repository.findManyCoursesByLecturerId(principal.getActiveRoleId());
		choices = SelectChoices.from(courses, "code", object.getCourse());
		lectures = this.repository.findManyLectures();
		choices2 = SelectChoices.from(lectures, "title", object.getLecture());
		tuple = super.unbind(object, "lecture", "course");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("lecture", choices2.getSelected().getKey());
		tuple.put("lectures", choices2);

		super.getResponse().setData(tuple);
	}

}
