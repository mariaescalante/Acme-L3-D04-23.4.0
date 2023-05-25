
package acme.testing.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Session;
import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantSessionTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(String username);

	@Query("select s from Session s where s.tutorial.assistant.userAccount.username = :username")
	Collection<Session> findManySessionsByAssistantUsername(String username);

}
