
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;
import acme.testing.lecturer.lecture.done.LecturerLectureTestRepository;

public class LecturerLectureCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecordIndex, final String title, final String abstract$, final String duration, final String body, final String link, final String theoreticalOrHandsOn) {
		// HINT: this test authenticates as an lecturer, list his or her courses, navigates
		// HINT+ to their lectures, and checks that they have the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("duration", duration);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("theoreticalOrHandsOn", theoreticalOrHandsOn);
		super.clickOnSubmit("Create");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(lectureRecordIndex, 0, title);
		super.checkColumnHasValue(lectureRecordIndex, 1, theoreticalOrHandsOn);
		super.checkColumnHasValue(lectureRecordIndex, 2, abstract$);

		super.clickOnListingRecord(lectureRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("duration", duration);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("theoreticalOrHandsOn", theoreticalOrHandsOn);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int courseRecordIndex, final int lectureRecordIndex, final String title, final String abstract$, final String duration, final String body, final String link, final String theoreticalOrHandsOn) {
		// HINT: this test attempts to create lectures using wrong data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("duration", duration);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("theoreticalOrHandsOn", theoreticalOrHandsOn);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a lecture for a course as a principal without 
		// HINT: the "Lecturer" role.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();
	}

}
