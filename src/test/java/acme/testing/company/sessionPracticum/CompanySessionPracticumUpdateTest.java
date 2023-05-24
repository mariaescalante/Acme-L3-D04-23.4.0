
package acme.testing.company.sessionPracticum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.SessionPracticum;
import acme.testing.TestHarness;

public class CompanySessionPracticumUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionPracticumTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sessionPracticumRecordIndex, final String title, final String abstract$, final String startDate, final String endDate, final String furtherInformationLink, final String correction) {
		// HINT: this test logs in as an company, lists his or her practicums, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List all session");
		super.clickOnListingRecord(sessionPracticumRecordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(sessionPracticumRecordIndex, 0, title);
		super.checkColumnHasValue(sessionPracticumRecordIndex, 1, startDate);
		super.checkColumnHasValue(sessionPracticumRecordIndex, 2, endDate);
		super.checkColumnHasValue(sessionPracticumRecordIndex, 3, correction);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int sessionPracticumRecordIndex, final String title, final String abstract$, final String startDate, final String endDate, final String furtherInformationLink, final String correction) {
		// HINT: this test attempts to update a practicum with wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List all session");
		super.clickOnListingRecord(sessionPracticumRecordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");
		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show a sessionPracticum of a practicum that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<SessionPracticum> sessionPracticums;
		String param;

		super.signIn("company1", "company1");
		sessionPracticums = this.repository.findManySessionPracticumsByCompanyUsername("company2");
		for (final SessionPracticum sessionPracticum : sessionPracticums)
			if (sessionPracticum.getPracticum().isDraftMode()) {
				param = String.format("id=%d", sessionPracticum.getPracticum().getId());

				super.checkLinkExists("Sign in");
				super.request("/company/sessionPracticum/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/sessionPracticum/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/sessionPracticum/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/sessionPracticum/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
