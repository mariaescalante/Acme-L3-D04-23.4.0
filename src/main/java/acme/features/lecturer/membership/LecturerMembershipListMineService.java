
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
public class LecturerMembershipListMineService extends AbstractService<Lecturer, Membership> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerMembershipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Membership> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyMembershipsByLecturerId(principal.getActiveRoleId());

		super.getBuffer().setData(objects);
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
		lectures = this.repository.findManyLecturesByLecturerId(principal.getActiveRoleId());
		choices2 = SelectChoices.from(lectures, "title", object.getLecture());
		tuple = super.unbind(object, "lecture", "course");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("lecture", choices2.getSelected().getKey());
		tuple.put("lectures", choices2);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Membership> objects) {
		assert objects != null;
	}

}
