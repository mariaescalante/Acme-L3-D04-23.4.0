
package acme.features.auditor.auditingRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecords;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordsListService extends AbstractService<Auditor, AuditingRecords> {

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
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(masterId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecords> objects;
		int auditId;

		auditId = super.getRequest().getData("id", int.class);
		objects = this.repository.findAuditingRecordsByAuditId(auditId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecords object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment");
		tuple.put("period", object.period());
		tuple.put("correction", object.isCorrection());
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecords> objects) {
		assert objects != null;

		int auditId;
		Audit audit;
		final boolean showCreate;
		final boolean exceptional;

		auditId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(auditId);
		showCreate = super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		exceptional = audit.isDraftMode();

		super.getResponse().setGlobal("id", auditId);

		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("exceptional", exceptional);
	}

}
