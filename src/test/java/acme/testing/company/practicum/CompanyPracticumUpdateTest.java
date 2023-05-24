
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int newrecordIndex, final String code, final String title, final String abstract$, final String goals, final String estimatedTotalTime, final String course) {
		// HINT: this test logs in as an company, lists his or her practicums, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkListingExists();

		super.clickOnListingRecord(newrecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abstract$, final String goals, final String estimatedTotalTime, final String course) {
		// HINT: this test attempts to update a practicum with wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a practicum with a role other than "Company",
		// HINT+ or using an company who is not the owner.

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums) {
			param = String.format("id=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
