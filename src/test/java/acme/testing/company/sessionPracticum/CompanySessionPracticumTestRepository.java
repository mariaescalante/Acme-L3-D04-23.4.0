
package acme.testing.company.sessionPracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.repositories.AbstractRepository;

public interface CompanySessionPracticumTestRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.userAccount.username = :username")
	Collection<Practicum> findManyPracticumsByCompanyUsername(String username);

	@Query("select sp from SessionPracticum sp where sp.practicum.company.userAccount.username = :username")
	Collection<SessionPracticum> findManySessionPracticumsByCompanyUsername(String username);

}
