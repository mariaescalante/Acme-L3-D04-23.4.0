
package acme.features.auditor.auditingRecords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordsShowService extends AbstractService<Auditor, AuditingRecords> {

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
	public void unbind(final AuditingRecords object) {
		assert object != null;
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "mark", "startDate", "endDate", "link");
		tuple.put("mark", choices.getSelected().getKey());
		tuple.put("mark2", choices);
		tuple.put("masterId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());
		final Double period = object.period();
		tuple.put("period", period);
		super.getResponse().setData(tuple);
	}

}
