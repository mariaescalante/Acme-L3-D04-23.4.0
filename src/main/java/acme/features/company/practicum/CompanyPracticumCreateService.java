
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
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

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
		Practicum object;
		Company company;

		company = this.repository.findOneCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Practicum();
		object.setDraftMode(true);
		object.setCompany(company);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "code", "title", "abstract$", "goals", "estimatedTotalTime");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (object.getCode() != null)
			if (!super.getBuffer().getErrors().hasErrors("code")) {
				Practicum existing;

				existing = this.repository.findOnePracticumByCode(object.getCode());
				super.state(existing == null, "code", "company.practicum.form.error.duplicated");
			}
		if (object.getEstimatedTotalTime() != null)
			if (!super.getBuffer().getErrors().hasErrors("estimatedTotalTime"))
				super.state(object.getEstimatedTotalTime() >= 0, "estimatedTotalTime", "company.practicum.form.error.negative-estimatedTotalTime");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

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

		super.getResponse().setData(tuple);
	}

}
