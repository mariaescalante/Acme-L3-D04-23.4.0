
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

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
		int practicumId;
		Practicum practicum;
		Company company;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		company = practicum == null ? null : practicum.getCompany();
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(company);

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
	public void bind(final Practicum object) {
		assert object != null;

		super.bind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime");

	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		final Double totalTime = object.totalTime(this.repository.findPracticumSessionsByPracticumId(object.getId()));
		if (object.getCode() != null)
			if (!super.getBuffer().getErrors().hasErrors("code")) {
				Practicum existing;
				existing = this.repository.findOnePracticumByCode(object.getCode());
				super.state(existing == null || existing.getId() == object.getId(), "code", "company.practicum.form.error.duplicated");
			}
		if (object.getEstimatedTotalTime() != null) {
			if (!super.getBuffer().getErrors().hasErrors("estimatedTotalTime"))
				super.state(object.getEstimatedTotalTime() >= 0, "estimatedTotalTime", "company.practicum.form.error.negative-estimatedTotalTime");
			if (!super.getBuffer().getErrors().hasErrors("estimatedTotalTime"))
				super.state(object.getEstimatedTotalTime() >= 0.9 * totalTime && object.getEstimatedTotalTime() <= 1.1 * totalTime, "estimatedTotalTime", "company.practicum.form.error.plus.or.less.10%");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choices;

		courses = this.repository.findManyCourse();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime");

		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("draftMode", object.isDraftMode());
		super.getResponse().setData(tuple);

	}

}
