/*
 * LecturerLectureDeleteService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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
public class LecturerMembershipDeleteService extends AbstractService<Lecturer, Membership> {

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
		status = super.getRequest().getPrincipal().hasRole(lecturer) || course != null && !course.isDraftMode();

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
	public void bind(final Membership object) {
		assert object != null;

		super.bind(object, "lecture", "course");
	}

	@Override
	public void validate(final Membership object) {
		assert object != null;
	}

	@Override
	public void perform(final Membership object) {
		assert object != null;

		this.repository.delete(object);
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

}
