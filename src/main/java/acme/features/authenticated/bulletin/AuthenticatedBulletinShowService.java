
package acme.features.authenticated.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinShowService extends AbstractService<Authenticated, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Bulletin bulletin;

		masterId = super.getRequest().getData("id", int.class);
		bulletin = this.repository.findOneBulletinById(masterId);
		status = bulletin != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Bulletin object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBulletinById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;
		String bulletin;
		Tuple tuple;

		bulletin = object.getTitle();

		tuple = super.unbind(object, "instantiationMoment", "title", "message", "flag", "url");
		tuple.put("Bulletin", bulletin);

		super.getResponse().setData(tuple);
	}

}
