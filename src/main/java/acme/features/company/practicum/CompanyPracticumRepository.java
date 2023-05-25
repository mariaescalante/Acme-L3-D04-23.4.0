
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.code = :code")
	Practicum findOnePracticumByCode(String code);

	@Query("select c from Company c where c.id = :id")
	Company findOneCompanyById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findManyPracticumsByCompanyId(int companyId);

	@Query("select p from Practicum p where p.draftMode = false")
	Collection<Practicum> findManyPracticums();

	@Query("select c from Course c")
	Collection<Course> findManyCourse();

	@Query("select ps from SessionPracticum ps where ps.practicum.id = :id")
	Collection<SessionPracticum> findPracticumSessionsByPracticumId(int id);
}
