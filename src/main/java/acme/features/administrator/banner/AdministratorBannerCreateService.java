
package acme.features.administrator.banner;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

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
		Banner object;

		object = new Banner();

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
		final Date InstantationMoment = MomentHelper.getCurrentMoment();
		if (object.getStart() != null && object.getEnd() != null)
			if (!super.getBuffer().getErrors().hasErrors("end"))
				super.state(object.getStart().before(object.getEnd()), "end", "company.practicum.form.error.start-before-end");
		if (object.getStart() != null)
			if (!super.getBuffer().getErrors().hasErrors("start"))
				super.state(object.getStart().after(InstantationMoment), "start", "company.practicum.form.error.start-future");
		if (object.getStart() != null && object.getEnd() != null)
			if (!super.getBuffer().getErrors().hasErrors("end")) {
				date = AdministratorBannerCreateService.sumarDiasAFecha(object.getStart(), 7);
				super.state(object.getEnd().after(date) || object.getEnd().equals(date), "end", "company.practicum.form.error.least-one-week-long");
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
	public static Date sumarDiasAFecha(final Date fecha, final int dias) {
		if (dias == 0)
			return fecha;
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}

}
