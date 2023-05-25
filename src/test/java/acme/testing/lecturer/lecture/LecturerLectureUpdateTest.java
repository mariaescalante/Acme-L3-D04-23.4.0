
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecordIndex, final int newLectureRecordIndex, final String title, final String abstract$, final String time, final String body, final String link, final String theoreticalOrHandsOn) {
		// HINT: this test logs in as an lecturer, lists his or her courses, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();

		super.clickOnListingRecord(lectureRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("time", time);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("theoreticalOrHandsOn", theoreticalOrHandsOn);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.checkColumnHasValue(newLectureRecordIndex, 0, title);
		super.checkColumnHasValue(newLectureRecordIndex, 1, theoreticalOrHandsOn);
		super.checkColumnHasValue(newLectureRecordIndex, 2, abstract$);

		super.clickOnListingRecord(newLectureRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("time", time);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("theoreticalOrHandsOn", theoreticalOrHandsOn);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int lectureRecordIndex, final String title, final String abstract$, final String time, final String body, final String link, final String theoreticalOrHandsOn) {
		// HINT: this test attempts to update a course with wrong data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();

		super.clickOnListingRecord(lectureRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("time", time);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("theoreticalOrHandsOn", theoreticalOrHandsOn);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a course with a role other than "Lecturer",
		// HINT+ or using an lecturer who is not the owner.

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures) {
			param = String.format("id=%d", lecture.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
