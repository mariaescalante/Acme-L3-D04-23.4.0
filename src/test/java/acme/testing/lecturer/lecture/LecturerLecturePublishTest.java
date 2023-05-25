
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLecturePublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecordIndex, final String title) {
		// HINT: this test authenticates as an lecturer, lists his or her lectures,
		// HINT: then selects one of them, and publishes it.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.checkColumnHasValue(lectureRecordIndex, 0, title);

		super.clickOnListingRecord(lectureRecordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int lectureRecordIndex, final String title, final String abstract$, final String time, final String body, final String link, final String theoreticalOrHandsOn) {
		// HINT: this test attempts to publish a lecture that cannot be published, yet.

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
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a lecture with a role other than "Lecturer".

		Collection<Lecture> lectures;
		String params;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				params = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a published lecture that was registered by the principal.

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer1", "lecturer1");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (!lecture.isDraftMode()) {
				params = String.format("id=%d", lecture.getId());
				super.request("/lecturer/lecture/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to publish a lecture that wasn't registered by the principal,
		// HINT+ be it published or unpublished.

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer2", "lecturer2");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures) {
			params = String.format("id=%d", lecture.getId());
			super.request("/lecturer/lecture/publish", params);
		}
		super.signOut();
	}

}
