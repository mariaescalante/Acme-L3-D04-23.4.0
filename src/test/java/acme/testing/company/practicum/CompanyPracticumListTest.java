
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticumListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$) {
		// HINT: this test signs in as an company, lists all of the practicums, 
		// HINT+ and then checks that the listing shows the expected data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, abstract$);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list of the practicums using 
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/company/practicum/list");
		super.checkPanicExists();
		super.signOut();
	}

}
