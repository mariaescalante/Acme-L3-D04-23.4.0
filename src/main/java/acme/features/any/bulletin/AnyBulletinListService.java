
package acme.features.any.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyBulletinListService extends AbstractService<Any, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyBulletinRepository repository;

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
		Collection<Bulletin> objects;
		objects = this.repository.findManyBulletin();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "message", "flag", "url");

		super.getResponse().setData(tuple);
	}

}
