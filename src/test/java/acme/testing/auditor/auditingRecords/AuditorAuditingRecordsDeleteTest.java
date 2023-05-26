
package acme.testing.auditor.auditingRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecords;
import acme.testing.TestHarness;

public class AuditorAuditingRecordsDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordsTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordsRecordIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("List all auditing records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkListingExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		Collection<AuditingRecords> manyAuditingRecords;
		String param;

		manyAuditingRecords = this.repository.findManyAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecords auditingRecords : manyAuditingRecords) {
			param = String.format("id=%d", auditingRecords.getAudit().getId());

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditingRecords/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/auditingRecords/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/auditor/auditingRecords/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/auditor/auditingRecords/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/auditingRecords/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditingRecords/show", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

	@Test
	public void test301Hacking() {

		Collection<AuditingRecords> manyAuditingRecords;
		String param;

		super.signIn("auditor1", "auditor1");
		manyAuditingRecords = this.repository.findManyAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecords auditingRecords : manyAuditingRecords)
			if (auditingRecords.getAudit().isDraftMode()) {
				param = String.format("id=%d", auditingRecords.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
