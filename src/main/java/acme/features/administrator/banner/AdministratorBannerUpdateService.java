
package acme.features.administrator.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "start", "end", "picture", "slogan", "url");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		Date date;

		if (object.getStart() != null && object.getEnd() != null)
			if (!super.getBuffer().getErrors().hasErrors("end"))
				super.state(object.getStart().before(object.getEnd()), "end", "company.practicum.form.error.start-before-end");

		if (object.getStart() != null)
			if (!super.getBuffer().getErrors().hasErrors("start"))
				super.state(object.getStart().after(object.getInstantiationMoment()), "start", "company.practicum.form.error.start-future");

		if (object.getStart() != null && object.getEnd() != null)
			if (!super.getBuffer().getErrors().hasErrors("end")) {
				date = AdministratorBannerCreateService.sumarDiasAFecha(object.getInstantiationMoment(), 7);
				super.state(object.getEnd().after(date) || object.getStart().equals(date), "end", "company.practicum.form.error.least-one-week-long");
			}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		object.setInstantiationMoment(MomentHelper.getCurrentMoment());
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "start", "end", "picture", "slogan", "url");

		super.getResponse().setData(tuple);
	}

}
