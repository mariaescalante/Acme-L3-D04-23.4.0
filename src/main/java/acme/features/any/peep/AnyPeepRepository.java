
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("SELECT p FROM Peep p")
	Collection<Peep> findAllPublishedPeeps();
	@Query("SELECT p FROM Peep p WHERE p.id = :id")
	Peep findPeepById(int id);
	@Query("SELECT a FROM UserAccount a WHERE a.id = :id")
	UserAccount findAccountById(int id);

}
