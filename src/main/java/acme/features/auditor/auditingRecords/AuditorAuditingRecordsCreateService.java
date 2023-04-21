
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
public class AuditorAuditingRecordsCreateService extends AbstractService<Auditor, AuditingRecords> {

	@Autowired
	protected AuditorAuditingRecordsRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditingRecordId;
		Audit audit;

		auditingRecordId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findAuditById(auditingRecordId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecords object;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findAuditById(auditId);

		object = new AuditingRecords();
		object.setAudit(audit);
		object.setCorrection(false);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecords object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "startDate", "endDate", "mark", "link");
	}

	@Override
	public void validate(final AuditingRecords object) {
		assert object != null;
		boolean confirmation;

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(object.getStartDate().before(object.getEndDate()), "endDate", "auditor.audit.form.error.start-before-end");

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(object.getStartDate().before(Date.from(Instant.now())), "startDate", "auditor.audit.form.error.start-before-end");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getEndDate().before(Date.from(Instant.now())), "endDate", "auditor.audit.form.error.start-before-end");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.period() > 1.0, "startDate", "auditor.audit.form.error.least-one-week-ahead");

		confirmation = object.getAudit().isDraftMode() ? true : super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

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
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}

}
