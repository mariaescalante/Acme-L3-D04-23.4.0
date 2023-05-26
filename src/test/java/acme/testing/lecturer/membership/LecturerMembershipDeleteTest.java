
package acme.testing.lecturer.membership;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Membership;
import acme.testing.TestHarness;

public class LecturerMembershipDeleteTest extends TestHarness {

	@Autowired
	protected LecturerMembershipTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/membership/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My membership");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
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
		// HINT: this test tries to delete an membership using principals with inappropriate roles

		Collection<Membership> memberships;
		String param;

		memberships = this.repository.findManyMembershipsByLecturerUsername("lecturer1");
		for (final Membership membership : memberships) {
			param = String.format("id=%d", membership.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete an membership with draftMode false

		Collection<Membership> memberships;
		String param;

		memberships = this.repository.findManyMembershipsByLecturerUsername("lecturer1");
		for (final Membership membership : memberships) {
			param = String.format("id=%d", membership.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/membership/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
