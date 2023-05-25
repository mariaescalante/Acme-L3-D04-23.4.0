
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecordIndex, final String title, final String theoreticalOrHandsOn, final String abstract$) {
		// HINT: this test authenticates as an lecturer, then lists his or her courses, 
		// HINT+ selects one of them, and check that it has the expected lectures.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(lectureRecordIndex, 0, title);
		super.checkColumnHasValue(lectureRecordIndex, 1, theoreticalOrHandsOn);
		super.checkColumnHasValue(lectureRecordIndex, 2, abstract$);
		super.clickOnListingRecord(lectureRecordIndex);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list the lectures of a course that is unpublished
		// HINT+ using a principal that didn't create it. 

		Collection<Lecture> lectures;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/list");
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/list");
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/lecturer/lecture/list");
				super.checkPanicExists();
				super.signOut();
			}
	}

}
