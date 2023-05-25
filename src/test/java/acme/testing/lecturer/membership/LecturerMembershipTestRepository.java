
package acme.testing.lecturer.membership;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Membership;
import acme.framework.repositories.AbstractRepository;

public interface LecturerMembershipTestRepository extends AbstractRepository {

	@Query("select c from Membership c where c.lecture.lecturer.userAccount.username = :username")
	Collection<Membership> findManyMembershipsByLecturerUsername(String username);

	@Query("select c from Membership c where c.lecture.lecturer.userAccount.username = :username")
	Collection<Membership> findManyAvailableMembershipsByLecturerUsername(String username);

}
