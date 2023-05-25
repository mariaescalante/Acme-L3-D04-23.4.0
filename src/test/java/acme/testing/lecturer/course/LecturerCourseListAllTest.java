
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseListAllTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstract$, final String price) {
		// HINT: this test signs in as an lecturer, lists all of the courses, 
		// HINT+ and then checks that the listing shows the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Courses");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, abstract$);
		super.checkColumnHasValue(recordIndex, 3, price);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list all of the courses using 
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/list-all");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/list-all");
		super.checkPanicExists();
		super.signOut();
	}

}
