
package acme.features.auditor.auditingRecords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		Mark mark;
		mark = super.getRequest().getData("mark", Mark.class);
		object.setMark(mark);
		super.bind(object, "subject", "assessment", "startDate", "endDate", "mark", "link");
	}

	@Override
	public void validate(final AuditingRecords object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			if (object.getStartDate() != null && object.getEndDate() != null)
				super.state(object.getStartDate().before(object.getEndDate()), "endDate", "auditor.audit.form.error.start-before-end");

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			if (object.getStartDate() != null && object.getEndDate() != null)
				super.state(object.getStartDate().after(MomentHelper.getCurrentMoment()), "startDate", "auditor.audit.form.error.start-before-moment");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			if (object.getStartDate() != null && object.getEndDate() != null)
				super.state(object.getEndDate().after(MomentHelper.getCurrentMoment()), "endDate", "auditor.audit.form.error.end-before-moment");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			if (object.getStartDate() != null && object.getEndDate() != null)
				super.state(object.period() >= 1.0, "endDate", "auditor.audit.form.error.least-one-hour-ahead");

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
		final SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "startDate", "endDate", "link");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());
		tuple.put("confirmation", false);
		tuple.put("mark", choices.getSelected().getKey());
		tuple.put("mark2", choices);
		super.getResponse().setData(tuple);
	}

}
