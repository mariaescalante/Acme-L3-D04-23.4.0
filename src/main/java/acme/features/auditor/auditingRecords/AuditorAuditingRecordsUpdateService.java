
package acme.features.auditor.auditingRecords;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordsUpdateService extends AbstractService<Auditor, AuditingRecords> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuditorAuditingRecordsRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditingRecordId;
		Audit audit;

		auditingRecordId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditByAuditingRecordId(auditingRecordId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecords object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordsById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecords object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "startDate", "endDate", "mark", "link");
	}

	@Override
	public void validate(final AuditingRecords object) {
		final Date date;
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(object.getStartDate().before(object.getEndDate()), "endDate", "auditor.auditing-records.form.error.start-before-end");

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(object.getStartDate().before(Date.from(Instant.now())), "startDate", "auditor.auditing-records.form.error.start-future");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getEndDate().before(Date.from(Instant.now())), "endDate", "auditor.auditing-records.form.error.end-future");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.period() > 1.0, "startDate", "auditor.auditing-records.form.error.at-least-an-hour");
	}

	@Override
	public void perform(final AuditingRecords object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecords object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "startDate", "endDate", "mark", "link");

		super.getResponse().setData(tuple);
	}

}
