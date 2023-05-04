
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Session;
import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.draftMode=false")
	Collection<Tutorial> findAllTutorials();

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select s from Session s where s.tutorial = :tutorial")
	Collection<Session> findManySessionByTutorial(Tutorial tutorial);
}
