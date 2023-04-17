
package acme.features.any.peep;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepPublishService extends AbstractService<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepRepository repository;

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
		Peep object;
		object = new Peep();
		object.setInstantiationMoment(Date.from(Instant.now()));
		object.setTitle("");
		object.setEmail("");
		object.setNickname("");
		object.setUrl("");
		object.setMessage("");

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "email", "nickname", "url", "message");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "title", "email", "nickname", "url", "message");

		super.getResponse().setData(tuple);
	}

}
