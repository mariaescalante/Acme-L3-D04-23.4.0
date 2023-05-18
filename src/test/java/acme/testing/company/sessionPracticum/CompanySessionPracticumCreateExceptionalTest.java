
package acme.testing.company.sessionPracticum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanySessionPracticumCreateExceptionalTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanySessionPracticumTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/sessionPracticum/create-positive-exceptional.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final int sessionPracticumRecordIndex, final String abstract$, final String startDate, final String endDate, final String title, final String furtherInformationLink, final String correction) {
		// HINT: this test authenticates as an company, list his or her practicums, navigates
		// HINT+ to their sessionPracticums, and checks that they have the expected data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("List all session");

		super.clickOnButton("Create exceptional session");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Create exceptional session");

		super.checkListingExists();
		super.sortListing(2, "asc");
		super.checkColumnHasValue(sessionPracticumRecordIndex, 0, correction);
		super.checkColumnHasValue(sessionPracticumRecordIndex, 1, title);
		super.checkColumnHasValue(sessionPracticumRecordIndex, 2, startDate);
		super.checkColumnHasValue(sessionPracticumRecordIndex, 3, endDate);

		super.clickOnListingRecord(sessionPracticumRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/sessionPracticum/create-negative-exceptional.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final int sessionPracticumRecordIndex, final String abstract$, final String startDate, final String endDate, final String title, final String furtherInformationLink) {
		// HINT: this test attempts to create sessionPracticums using wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("List all session");

		super.clickOnButton("Create excpetional session");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnButton("Confirm exceptional practicum session");
		super.clickOnSubmit("Create exceptional session");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a sessionPracticum for a practicum as a principal without 
		// HINT: the "Company" role.

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/sessionPracticum/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/sessionPracticum/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/sessionPracticum/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a sessionPracticum for a published practicum created by 
		// HINT+ the principal.

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums)
			if (!practicum.isDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/sessionPracticum/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create sessionPracticums for practicums that weren't created 
		// HINT+ by the principal.

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company2");
		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());
			super.request("/company/sessionPracticum/create", param);
			super.checkPanicExists();
		}
	}

}
