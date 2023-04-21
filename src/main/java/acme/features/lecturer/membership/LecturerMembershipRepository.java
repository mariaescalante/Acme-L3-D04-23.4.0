
package acme.features.lecturer.membership;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.Membership;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerMembershipRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select m from Membership m where m.course.lecturer.id = :lecturerId")
	Collection<Membership> findManyMembershipsByLecturerId(int lecturerId);

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findManyLecturesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findManyCoursesByLecturerId(int lecturerId);

	@Query("select m from Membership m where m.course.id = :masterId")
	Collection<Membership> findManyMembershipByMasterId(int masterId);

	@Query("select m.course from Membership m where m.id = :id")
	Course findOneCourseByMembershipId(int id);

	@Query("select m from Membership m where m.id = :id")
	Membership findOneMembershipById(int id);
}
