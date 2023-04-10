
package acme.features.any.practica;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPracticaRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.course.id = :masterId")
	Collection<Practicum> findManyPracticumsByCourseId(int masterId);

}
