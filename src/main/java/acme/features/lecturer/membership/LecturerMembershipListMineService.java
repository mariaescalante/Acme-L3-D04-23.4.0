
package acme.features.lecturer.membership;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Membership;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerMembershipListMineService extends AbstractService<Lecturer, Membership> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerMembershipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Membership> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyMembershipsByLecturerId(principal.getActiveRoleId());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Membership object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "title");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("lecture", object.getLecture().getTitle());

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Membership> objects) {
		assert objects != null;
	}

}
