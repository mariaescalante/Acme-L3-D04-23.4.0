/*
 * LecturerLectureCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseType;
import acme.entities.Lecture;
import acme.entities.Membership;
import acme.features.lecturer.membership.LecturerMembershipRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository		repository;

	@Autowired
	protected LecturerMembershipRepository	MemRepository;

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
		Lecture object;

		object = new Lecture();
		object.setTitle("");
		object.setAbstract$("");
		object.setTime(0);
		object.setBody("");
		object.setTheoreticalOrHandsOn(CourseType.THEORETICAL);
		object.setLink("");
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		CourseType theoreticalOrHandsOn;
		theoreticalOrHandsOn = super.getRequest().getData("theoreticalOrHandsOn", CourseType.class);

		object.setTheoreticalOrHandsOn(theoreticalOrHandsOn);
		super.bind(object, "title", "abstract$", "time", "body", "link");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		final String courseId;
		final Course course;
		final Membership membership = new Membership();
		System.out.println(super.getRequest().toString());
		courseId = super.getRequest().getData("course", String.class);
		course = this.repository.findOneCourseById(Integer.parseInt(courseId));
		membership.setCourse(course);
		membership.setLecture(object);
		this.repository.save(object);
		this.MemRepository.save(membership);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		Principal principal;
		Collection<Course> courses;
		final SelectChoices choices;
		final SelectChoices choices2;
		List<Course> courses2;
		Membership m;

		m = this.repository.findOneMembershipByLectureId(object.getId());
		principal = super.getRequest().getPrincipal();
		courses = this.repository.findManyCoursesByLecturerId(principal.getActiveRoleId());
		if (m == null) {
			courses2 = new ArrayList<>(courses);
			choices2 = SelectChoices.from(courses, "code", courses2.get(0));
		} else
			choices2 = SelectChoices.from(courses, "code", m.getCourse());
		choices = SelectChoices.from(CourseType.class, object.getTheoreticalOrHandsOn());

		tuple = super.unbind(object, "title", "abstract$", "time", "body", "link");

		tuple.put("course", choices2.getSelected().getKey());
		tuple.put("courses", choices2);
		tuple.put("theoreticalOrHandsOn", choices.getSelected().getKey());
		tuple.put("theoreticalOrHandsOn2", choices);
		super.getResponse().setData(tuple);
	}

}
