
package acme.testing.auditor.auditingRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordsCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordsTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {
		// HINT: this test authenticates as an auditor, list his or her audits, navigates
		// HINT+ to their auditingRecords, and checks that they have the expected data.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("AuditingRecords");

		super.clickOnButton("Create");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(auditingRecordsRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordsRecordIndex, 1, startDate);

		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecords/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {
		// HINT: this test attempts to create auditingRecords using wrong data.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("AuditingRecords");

		super.clickOnButton("Create");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a auditingRecords for a audit as a principal without 
		// HINT: the "Auditor" role.

		Collection<Audit> audits;
		String param;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditingRecords/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditingRecords/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/auditingRecords/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a auditingRecords for a published audit created by 
		// HINT+ the principal.

		Collection<Audit> audits;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (!audit.isDraftMode()) {
				param = String.format("masterId=%d", audit.getId());
				super.request("/auditor/auditingRecords/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create auditingRecords for audits that weren't created 
		// HINT+ by the principal.

		Collection<Audit> audits;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		audits = this.repository.findManyAuditsByAuditorUsername("auditor2");
		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());
			super.request("/auditor/auditingRecords/create", param);
			super.checkPanicExists();
		}
	}

}
