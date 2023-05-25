
package acme.testing.lecturer.membership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerMembershipListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/membership/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String course, final String lecture) {
		// HINT: this test authenticates as an lecturer, lists his or her memberships only,
		// HINT+ and then checks that the listing has the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My membership");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, course);
		super.checkColumnHasValue(recordIndex, 2, lecture);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/lecturer/membership/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/membership/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/membership/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
