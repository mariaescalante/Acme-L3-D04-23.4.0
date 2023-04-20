
package acme.features.any.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyAuditRepository extends AbstractRepository {

	@Query("select p from Audit p where p.id = :id")
	Audit findOneAuditById(int id);

	@Query("select p from Audit p where p.course.id = :masterId")
	Collection<Audit> findManyAuditsByCourseId(int masterId);

}
