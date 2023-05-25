
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseType;
import acme.entities.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

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
		int lectureId;
		Lecture lecture;
		Lecturer lecturer;

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		lecturer = lecture.getLecturer();
		status = lecture != null && lecture.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
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
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "time", "body", "theoreticalOrHandsOn", "link");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(CourseType.class, object.getTheoreticalOrHandsOn());

		tuple = super.unbind(object, "title", "abstract$", "time", "body", "theoreticalOrHandsOn", "link");
		tuple.put("theoreticalOrHandsOn", choices.getSelected().getKey());
		tuple.put("theoreticalOrHandsOn2", choices);
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
