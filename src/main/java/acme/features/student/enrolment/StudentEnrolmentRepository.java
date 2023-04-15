
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("select e from Enrolment e where e.student.id = :studentId")
	Collection<Enrolment> findManyEnrolmentsByStudentId(int studentId);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select e from Student e where e.id = :id")
	Student findOneStudentById(int id);

	@Query("select e from Activity e where e.enrolment.id = :enrolmentId")
	Collection<Activity> findManyActivitiesByEnrolmentId(int enrolmentId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select a from Activity a where a.enrolment = :enrolment")
	Collection<Activity> findAllActivitiesByEnrolment(Enrolment enrolment);

	@Query("select e from Enrolment e where e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

}
