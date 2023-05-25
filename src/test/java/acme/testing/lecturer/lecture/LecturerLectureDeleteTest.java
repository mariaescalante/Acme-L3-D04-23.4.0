
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecordIndex) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.clickOnListingRecord(lectureRecordIndex);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkListingExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete an lecture using principals with inappropriate roles

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures) {
			param = String.format("id=%d", lecture.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete an lecture with draftMode false

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (!lecture.isDraftMode()) {
				param = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/lecturer/lecture/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
