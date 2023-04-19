
package acme.features.administrator.offer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		final Offer object = new Offer();
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "startDate", "endDate", "price", "optionalLink");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (object.getInstantiationMoment() != null && object.getStartDate() != null && object.getEndDate() != null) {
			//El momento de instanciacion debe ser minimo un dia anterior a la fecha de inicio de la oferta
			final LocalDateTime date1 = LocalDateTime.ofInstant(object.getInstantiationMoment().toInstant(), ZoneId.systemDefault());
			final LocalDateTime date2 = LocalDateTime.ofInstant(object.getStartDate().toInstant(), ZoneId.systemDefault());

			final int numeroDeDias2 = 1;
			final int convertidorDiasMinutos2 = numeroDeDias2 * 24 * 60;
			final long minutesBetween2 = ChronoUnit.MINUTES.between(date1, date2);

			if (!super.getBuffer().getErrors().hasErrors("instantiationMoment") && !super.getBuffer().getErrors().hasErrors("startDate")) {
				super.state(minutesBetween2 >= convertidorDiasMinutos2, "instantiationMoment", "administrator.offer.form.error.instantiationMoment");
				super.state(minutesBetween2 >= convertidorDiasMinutos2, "startDate", "administrator.offer.form.error.startInstantiation");
			}

			//La fecha de inicio debe ser anterior a la fecha de fin
			if (!super.getBuffer().getErrors().hasErrors("instantiationMoment") && !super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate")) {
				super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "administrator.offer.form.error.startDate");
				super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "endDate", "administrator.offer.form.error.endDate");
			}

			//La oferta debe durar al menos una semana
			final LocalDateTime date3 = LocalDateTime.ofInstant(object.getStartDate().toInstant(), ZoneId.systemDefault());
			final LocalDateTime date4 = LocalDateTime.ofInstant(object.getEndDate().toInstant(), ZoneId.systemDefault());

			final int numeroDeDias1 = 7;
			final int convertidorDiasMinutos1 = numeroDeDias1 * 24 * 60;
			final long minutesBetween1 = ChronoUnit.MINUTES.between(date3, date4);

			if (!super.getBuffer().getErrors().hasErrors("instantiationMoment") && !super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate")) {
				super.state(minutesBetween1 >= convertidorDiasMinutos1, "endDate", "administrator.offer.form.error.oneWeek");
				super.state(minutesBetween1 >= convertidorDiasMinutos1, "startDate", "administrator.offer.form.error.oneWeek");
			}
		} else
			super.state(true, "endDate", "administrator.offer.form.error.endDate");

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "startDate", "endDate", "price", "optionalLink");

		super.getResponse().setData(tuple);
	}
}
