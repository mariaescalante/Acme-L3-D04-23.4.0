
package acme.testing.auditor.auditingRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecords;
import acme.testing.TestHarness;

public class AuditorAuditingRecordsShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordsTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final String reference, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {
		// HINT: this test signs in as an auditor, lists his or her audits, selects
		// HINT+ one of them and checks that it's as expected.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("AuditingRecords");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show a auditingRecords of a audit that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<AuditingRecords> auditingRecords;
		String param;

		super.signIn("auditor1", "auditor1");
		auditingRecords = this.repository.findManyAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecords auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().isDraftMode()) {
				param = String.format("id=%d", auditingRecord.getAudit().getId());

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

				super.signIn("company1", "company1");
				super.request("/auditor/auditingRecords/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
