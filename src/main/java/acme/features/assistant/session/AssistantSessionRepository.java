
package acme.features.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Session;
import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	//@Query("select t from Tutorial t where t.assistant.id = :id")
	//Collection<Tutorial> findManyTutorialsByAssistantId(int id);

	//@Query("select c from Course c")
	//Collection<Course> findAllCourses();

	//@Query("select s from Session s where s.tutorial = :tutorial")
	//Collection<Session> findManySessionByTutorial(Tutorial tutorial);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	//@Query("select a from Assistant a where a.id = :id")
	//Assistant findOneAssistantById(int id);

	//@Query("select c from Course c where c.id = :id")
	//Course findOneCourseById(int id);

	//@Query("select t from Tutorial t where t.code = :code")
	//Tutorial findOneTutorialByCode(String code);

	@Query("select s from Session s where s.tutorial.id = :id")
	Collection<Session> findManySessionsByTutorialId(int id);

	@Query("select s from Session s where s.id = :id")
	Session findOneSessionById(int id);

	@Query("select s.tutorial from Session s where s.id = :id")
	Tutorial findOneTutorialBySesionId(int id);
}
