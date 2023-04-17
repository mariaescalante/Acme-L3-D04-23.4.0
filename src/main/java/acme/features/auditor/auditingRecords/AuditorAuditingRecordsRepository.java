
package acme.features.auditor.auditingRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditingRecordsRepository extends AbstractRepository {

	@Query("select p from Audit p where p.id = :id")
	Audit findAuditById(int id);

	@Query("select p from Auditor p where p.id = :id")
	Auditor findAuditorId(int id);

	@Query("select ps from AuditingRecords ps where ps.audit.id = :id")
	Collection<AuditingRecords> findAuditingRecordsByAuditId(int id);

	@Query("select p from AuditingRecords p where p.id = :id")
	AuditingRecords findAuditingRecordsById(int id);

	@Query("select p.audit from AuditingRecords p where p.id = :auditingRecordId")
	Audit findAuditByAuditingRecordId(int auditingRecordId);

}
