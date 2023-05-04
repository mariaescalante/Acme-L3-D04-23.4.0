/*
 * EmployerJobDeleteService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.entities.Membership;
import acme.entities.Practicum;
import acme.entities.Session;
import acme.entities.SessionPracticum;
import acme.entities.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
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
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abstract$", "price", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		Collection<Membership> membership;
		Collection<Practicum> practicums;
		Collection<Audit> audits;
		Collection<AuditingRecords> ar;
		Collection<SessionPracticum> sp;
		Collection<Enrolment> enrolments;
		Collection<Tutorial> tutorials;
		Collection<Activity> a;
		Collection<Session> s;
		membership = this.repository.findMembershipByCourseId(object.getId());
		practicums = this.repository.findPracticumByCourseId(object.getId());
		enrolments = this.repository.findEnrolmentByCourseId(object.getId());
		tutorials = this.repository.findTutorialByCourseId(object.getId());
		audits = this.repository.findAuditByCourseId(object.getId());
		for (final Audit audit : audits) {
			ar = this.repository.findAuditingRecordByAuditId(audit.getId());
			this.repository.deleteAll(ar);
		}
		for (final Practicum practicum : practicums) {
			sp = this.repository.findSessionPracticumByPracticumId(practicum.getId());
			this.repository.deleteAll(sp);
		}
		for (final Enrolment enrolment : enrolments) {
			a = this.repository.findActivityByEnrolmentId(enrolment.getId());
			this.repository.deleteAll(a);
		}
		for (final Tutorial tutorial : tutorials) {
			s = this.repository.findSessionByTutorialId(tutorial.getId());
			this.repository.deleteAll(s);
		}
		this.repository.deleteAll(membership);
		this.repository.deleteAll(audits);
		this.repository.deleteAll(practicums);
		this.repository.deleteAll(enrolments);
		this.repository.deleteAll(tutorials);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstract$", "price", "link");
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
