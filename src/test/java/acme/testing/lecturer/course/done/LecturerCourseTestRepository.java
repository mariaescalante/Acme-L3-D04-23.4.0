
package acme.testing.lecturer.course.done;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Course;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseTestRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.userAccount.username = :username")
	Collection<Course> findManyCoursesByLecturerUsername(String username);

	@Query("select c from Course c where c.lecturer.userAccount.username = :username and c.draftMode = true")
	Collection<Course> findManyAvailableCoursesByLecturerUsername(String username);

}
