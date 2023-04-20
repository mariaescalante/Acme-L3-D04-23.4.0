
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select p.practicum from SessionPracticum p where p.id = :id")
	Practicum findPracticumBySessionId(int id);

	@Query("select ps from SessionPracticum ps where ps.practicum.id = :id")
	Collection<SessionPracticum> findPracticumSessionsByPracticumId(int id);

	@Query("select p from SessionPracticum p where p.id = :id")
	SessionPracticum findSessionById(int id);

}
