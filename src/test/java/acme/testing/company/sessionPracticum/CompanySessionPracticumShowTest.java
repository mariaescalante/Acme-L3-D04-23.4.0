//
//package acme.testing.company.sessionPracticum;
//
//import java.util.Collection;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import acme.entities.SessionPracticum;
//import acme.testing.TestHarness;
//
//public class CompanySessionPracticumShowTest extends TestHarness {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected CompanySessionPracticumTestRepository repository;
//
//	// Test methods -----------------------------------------------------------
//
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/company/sessionPracticum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int practicumRecordIndex, final String reference, final int sessionPracticumRecordIndex, final String title, final String description, final String furtherInformationLink, final String abstract$, final String endDate,
//		final String startDate) {
//		// HINT: this test signs in as an company, lists his or her practicums, selects
//		// HINT+ one of them and checks that it's as expected.
//
//		super.signIn("company1", "company1");
//
//		super.clickOnMenu("Company", "Practicum");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//		super.clickOnListingRecord(practicumRecordIndex);
//		super.clickOnButton("List all session");
//		super.checkListingExists();
//		super.clickOnListingRecord(sessionPracticumRecordIndex);
//		super.checkFormExists();
//
//		super.checkInputBoxHasValue("title", title);
//		super.checkInputBoxHasValue("abstract$", abstract$);
//		super.checkInputBoxHasValue("startDate", startDate);
//		super.checkInputBoxHasValue("endDate", endDate);
//		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);
//
//		super.signOut();
//	}
//
//	@Test
//	public void test200Negative() {
//		// HINT: there's no negative test case for this listing, since it doesn't
//		// HINT+ involve filling in any forms.
//	}
//
//	@Test
//	public void test300Hacking() {
//		// HINT: this test tries to show a sessionPracticum of a practicum that is in draft mode or
//		// HINT+ not available, but wasn't published by the principal;
//
//		Collection<SessionPracticum> sessionPracticums;
//		String param;
//
//		super.signIn("company1", "company1");
//		sessionPracticums = this.repository.findManySessionPracticumsByCompanyUsername("company2");
//		for (final SessionPracticum sessionPracticum : sessionPracticums)
//			if (sessionPracticum.getPracticum().isDraftMode()) {
//				param = String.format("id=%d", sessionPracticum.getPracticum().getId());
//
//				super.checkLinkExists("Sign in");
//				super.request("/company/sessionPracticum/show", param);
//				super.checkPanicExists();
//
//				super.signIn("administrator", "administrator");
//				super.request("/company/sessionPracticum/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("company2", "company2");
//				super.request("/company/sessionPracticum/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("lecturer1", "lecturer1");
//				super.request("/company/sessionPracticum/show", param);
//				super.checkPanicExists();
//				super.signOut();
//			}
//	}
//
//}
