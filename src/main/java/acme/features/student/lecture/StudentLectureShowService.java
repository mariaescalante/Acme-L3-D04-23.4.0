
package acme.features.student.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseType;
import acme.entities.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureShowService extends AbstractService<Student, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentLectureRepository repository;

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
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(CourseType.class, object.getTheoreticalOrHandsOn());

		tuple = super.unbind(object, "title", "abstract$", "time", "body", "theoreticalOrHandsOn", "link");
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("theoreticalOrHandsOn", choices.getSelected().getKey());
		tuple.put("theoreticalOrHandsOn2", choices);

		super.getResponse().setData(tuple);
	}

}
