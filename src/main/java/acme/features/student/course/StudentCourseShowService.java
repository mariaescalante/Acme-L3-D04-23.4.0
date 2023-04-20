
package acme.features.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(CourseType.class, object.getTheoreticalOrHandsOn());

		tuple = super.unbind(object, "code", "title", "abstract$", "price", "link");
		tuple.put("lecturer", object.getLecturer().getAlmaMater());
		tuple.put("theoreticalOrHandsOn", choices.getSelected().getKey());
		tuple.put("theoreticalOrHandsOn2", choices);
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
