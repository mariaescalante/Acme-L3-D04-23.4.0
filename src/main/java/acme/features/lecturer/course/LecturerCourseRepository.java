/*
 * LecturerCourseRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.entities.Course;
import acme.entities.CourseType;
import acme.entities.Enrolment;
import acme.entities.Lecture;
import acme.entities.Membership;
import acme.entities.Practicum;
import acme.entities.Session;
import acme.entities.SessionPracticum;
import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecturer findOneLectureById(int id);

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findManyCoursesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findManyCourses();

	@Query("select m.lecture from Membership m where m.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

	@Query("select m from Membership m where m.course.id = :courseId")
	Collection<Membership> findMembershipByCourseId(int courseId);

	@Query("select p from Practicum p where p.course.id = :courseId")
	Collection<Practicum> findPracticumByCourseId(int courseId);

	@Query("select a from Audit a where a.course.id = :courseId")
	Collection<Audit> findAuditByCourseId(int courseId);

	@Query("select e from Enrolment e where e.course.id = :courseId")
	Collection<Enrolment> findEnrolmentByCourseId(int courseId);

	@Query("select t from Tutorial t where t.course.id = :courseId")
	Collection<Tutorial> findTutorialByCourseId(int courseId);

	@Query("select a from AuditingRecords a where a.audit.id = :auditId")
	Collection<AuditingRecords> findAuditingRecordByAuditId(int auditId);

	@Query("select s from SessionPracticum s where s.practicum.id = :practicumId")
	Collection<SessionPracticum> findSessionPracticumByPracticumId(int practicumId);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findActivityByEnrolmentId(int enrolmentId);

	@Query("select s from Session s where s.tutorial.id = :tutorialId")
	Collection<Session> findSessionByTutorialId(int tutorialId);

	@Query("select count(m.lecture) from Membership m where m.course.id = :courseId")
	Integer countLecturesByCourseId(int courseId);

	@Query("select count(m.lecture) from Membership m where m.course.id = :courseId and m.lecture.theoreticalOrHandsOn = :type")
	Integer countTheoreticalLecturesByCourseId(int courseId, CourseType type);

	@Query("select m.lecture from Membership m where m.course.id = :courseId and m.lecture.draftMode = true")
	Collection<Lecture> findNotPublishedLecturesByCourseId(int courseId);
}
