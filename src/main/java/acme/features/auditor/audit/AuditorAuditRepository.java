
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select p from Audit p where p.id = :id")
	Audit findOneAuditById(int id);

	@Query("select p from Audit p where p.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select c from Auditor c where c.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select p from Audit p where p.auditor.id = :auditorId")
	Collection<Audit> findManyAuditsByAuditorId(int auditorId);

	@Query("select p from Audit p where p.draftMode = false")
	Collection<Audit> findManyAudits();

	@Query("select c from Course c")
	Collection<Course> findManyCourse();

}
