
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerCoursePublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerCourseTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {
		// HINT: this test authenticates as an lecturer, lists his or her courses,
		// HINT: then selects one of them, and publishes it.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abstract$, final String price, final String link) {
		// HINT: this test attempts to publish a course that cannot be published, yet.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a course with a role other than "Lecturer".

		Collection<Course> courses;
		String params;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer2");
		for (final Course course : courses)
			if (course.isDraftMode()) {
				params = String.format("id=%d", course.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/lecturer/course/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a published course that was registered by the principal.

		Collection<Course> courses;
		String params;

		super.signIn("lecturer2", "lecturer2");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer2");
		for (final Course course : courses)
			if (!course.isDraftMode()) {
				params = String.format("id=%d", course.getId());
				super.request("/lecturer/course/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to publish a course that wasn't registered by the principal,
		// HINT+ be it published or unpublished.

		Collection<Course> courses;
		String params;

		super.signIn("lecturer1", "lecturer1");
		courses = this.repository.findManyCoursesByLecturerUsername("lecturer2");
		for (final Course course : courses) {
			params = String.format("id=%d", course.getId());
			super.request("/lecturer/course/publish", params);
		}
		super.signOut();
	}

}
