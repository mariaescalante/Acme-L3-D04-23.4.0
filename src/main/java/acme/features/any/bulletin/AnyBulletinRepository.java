
package acme.features.any.bulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyBulletinRepository extends AbstractRepository {

	@Query("select p from Bulletin p where p.id = :id")
	Bulletin findOneBulletinById(int id);

	@Query("select p from Bulletin p")
	Collection<Bulletin> findManyBulletin();

}
