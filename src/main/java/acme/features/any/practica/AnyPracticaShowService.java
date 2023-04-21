
package acme.features.any.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPracticaShowService extends AbstractService<Any, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPracticaRepository repository;

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
		Practicum practica;

		masterId = super.getRequest().getData("id", int.class);
		practica = this.repository.findOnePracticumById(masterId);
		status = practica != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		String company;
		Tuple tuple;

		company = object.getCompany().getName();

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime");
		tuple.put("company", company);

		super.getResponse().setData(tuple);
	}

}
