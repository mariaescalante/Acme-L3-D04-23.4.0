
package acme.testing.lecturer.membership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerMembershipCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/membership/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String course, final String lecture) {
		// HINT: this test authenticates as an lecturer and then lists his or her
		// HINT: memberships, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My membership");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);
		super.clickOnSubmit("Create");
		super.signOut();
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My membership");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, course);
		super.checkColumnHasValue(recordIndex, 2, lecture);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("lecture", lecture);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/membership/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String course, final String lecture) {
		// HINT: this test attempts to create memberships with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My membership");
		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a membership using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/membership/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/membership/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/membership/create");
		super.checkPanicExists();
		super.signOut();
	}

}
