
package acme.testing.auditor.auditingRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecords;
import acme.testing.TestHarness;

public class AuditorAuditingRecordsUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordsTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordsRecordIndex, final int newAuditingRecordsRecordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark,
		final String link) {
		// HINT: this test logs in as an auditor, lists his or her audits, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("List all auditing records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);

		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.checkColumnHasValue(newAuditingRecordsRecordIndex, 0, subject);
		super.checkColumnHasValue(newAuditingRecordsRecordIndex, 1, assessment);

		super.clickOnListingRecord(newAuditingRecordsRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark) {
		// HINT: this test attempts to update a audit with wrong data.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("List all auditing records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a audit with a role other than "Auditor",
		// HINT+ or using an auditor who is not the owner.

		Collection<AuditingRecords> auditingRecords;
		String param;

		super.signIn("auditor1", "auditor1");
		auditingRecords = this.repository.findManyAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecords auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().isDraftMode()) {
				param = String.format("id=%d", auditingRecord.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
