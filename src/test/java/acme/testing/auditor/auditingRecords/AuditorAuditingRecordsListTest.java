
package acme.testing.auditor.auditingRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordsListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordsTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final String code, final int auditingRecordsRecordIndex, final String correction, final String subject, final String assesment) {
		// HINT: this test authenticates as an auditor, then lists his or her audits, 
		// HINT+ selects one of them, and check that it has the expected auditingRecords.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(auditRecordIndex, 0, code);
		super.clickOnListingRecord(auditRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnButton("AuditingRecords");

		super.checkListingExists();
		super.checkColumnHasValue(auditingRecordsRecordIndex, 0, correction);
		super.checkColumnHasValue(auditingRecordsRecordIndex, 1, subject);
		super.checkColumnHasValue(auditingRecordsRecordIndex, 1, assesment);
		super.clickOnListingRecord(auditingRecordsRecordIndex);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list the auditingRecords of a audit that is unpublished
		// HINT+ using a principal that didn't create it. 

		Collection<Audit> audits;
		String param;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (audit.isDraftMode()) {
				param = String.format("masterId=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditingRecords/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditingRecords/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditingRecords/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/auditor/auditingRecords/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
