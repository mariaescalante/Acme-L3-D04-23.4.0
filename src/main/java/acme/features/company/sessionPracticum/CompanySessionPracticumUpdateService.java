
package acme.features.company.sessionPracticum;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumSessionId;
		Practicum practicum;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumBySessionId(practicumSessionId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "startDate", "endDate", "furtherInformationLink");
	}

	@Override
	public void validate(final SessionPracticum object) {
		Date date;
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getStartDate().before(object.getEndDate()), "endDate", "company.practicum.form.error.start-before-end");

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			date = CompanySessionPracticumCreateService.sumarDiasAFecha(MomentHelper.getCurrentMoment(), 7);
			super.state(object.getStartDate().after(date) || object.getStartDate().equals(date), "startDate", "company.practicum.form.error.least-one-week-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			date = CompanySessionPracticumCreateService.sumarDiasAFecha(object.getStartDate(), 7);
			super.state(object.getEndDate().after(date) || object.getEndDate().equals(date), "endDate", "company.practicum.form.error.least-one-week-long");
		}
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "startDate", "endDate", "furtherInformationLink");

		super.getResponse().setData(tuple);
	}

}
