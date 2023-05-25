
package acme.testing.lecturer.membership;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Membership;
import acme.testing.TestHarness;

public class LecturerMembershipShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerMembershipTestRepository repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/membership/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String course, final String lecture) {
		// HINT: this test signs in as an lecturer, lists all of the memberships, click on  
		// HINT+ one of them, and checks that the form has the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My membership");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("lecture", lecture);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished membership by someone who is not the principal.

		Collection<Membership> memberships;
		String param;

		memberships = this.repository.findManyAvailableMembershipsByLecturerUsername("lecturer1");
		for (final Membership membership : memberships) {
			param = String.format("id=%d", membership.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/membership/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/membership/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/membership/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/membership/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
