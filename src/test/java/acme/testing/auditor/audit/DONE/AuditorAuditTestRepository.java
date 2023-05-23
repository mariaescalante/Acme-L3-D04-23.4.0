
package acme.testing.auditor.audit.DONE;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	Collection<Audit> findManyAuditsByAuditorUsername(String username);

	@Query("select ar from AuditingRecords ar where ar.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecords> findManyAuditingRecordsByAuditorUsername(String username);

}
