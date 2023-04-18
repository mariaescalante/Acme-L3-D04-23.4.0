
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.Membership;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findManyCoursesByLecturerId(int lecturerId);

	@Query("select c from Course c")
	Collection<Course> findManyCourses();

	@Query("select m from Membership m where m.lecture.id = :lectureId")
	Collection<Membership> findMembershipByLectureId(int lectureId);

	@Query("select m from Membership m where m.lecture.id = :lectureId")
	Membership findOneMembershipByLectureId(int lectureId);

	@Query("select m.lecture from Membership m where m.course.id = :masterId and m.lecture.draftMode = false")
	Collection<Lecture> findManyLecturesByMasterId(int masterId);

	@Query("select m.lecture from Membership m where m.course.lecturer.id = :lecturerId")
	Collection<Lecture> findManyLecturesByLecturerId(int lecturerId);

	@Query("select l from Lecture l")
	Collection<Lecture> findManyLectures();

	@Query("select m.course.lecturer.id from Membership m where m.lecture.id = :lectureId")
	Collection<Integer> findManyLecturersByLectureId(int lectureId);
}
