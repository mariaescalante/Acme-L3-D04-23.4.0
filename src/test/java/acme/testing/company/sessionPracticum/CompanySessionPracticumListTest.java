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
//import acme.entities.Practicum;
//import acme.testing.TestHarness;
//
//public class CompanySessionPracticumListTest extends TestHarness {
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
//	@CsvFileSource(resources = "/company/sessionPracticum/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int practicumRecordIndex, final String code, final int sessionPracticumRecordIndex, final String title, final String startDate, final String endDate, final String correction) {
//		// HINT: this test authenticates as an company, then lists his or her practicums, 
//		// HINT+ selects one of them, and check that it has the expected sessionPracticums.
//
//		super.signIn("company1", "company1");
//
//		super.clickOnMenu("Company", "Practicum");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.checkColumnHasValue(practicumRecordIndex, 0, code);
//		super.clickOnListingRecord(practicumRecordIndex);
//		super.checkInputBoxHasValue("code", code);
//		super.clickOnButton("List all session");
//
//		super.checkListingExists();
//		super.checkColumnHasValue(sessionPracticumRecordIndex, 0, correction);
//		super.checkColumnHasValue(sessionPracticumRecordIndex, 1, title);
//		super.checkColumnHasValue(sessionPracticumRecordIndex, 2, startDate);
//		super.checkColumnHasValue(sessionPracticumRecordIndex, 3, endDate);
//		super.clickOnListingRecord(sessionPracticumRecordIndex);
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
//		// HINT: this test tries to list the sessionPracticums of a practicum that is unpublished
//		// HINT+ using a principal that didn't create it. 
//
//		Collection<Practicum> practicums;
//		String param;
//
//		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
//		for (final Practicum practicum : practicums)
//			if (practicum.isDraftMode()) {
//				param = String.format("masterId=%d", practicum.getId());
//
//				super.checkLinkExists("Sign in");
//				super.request("/company/sessionPracticum/list", param);
//				super.checkPanicExists();
//
//				super.signIn("administrator", "administrator");
//				super.request("/company/sessionPracticum/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("company2", "company2");
//				super.request("/company/sessionPracticum/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("lecturer1", "lecturer1");
//				super.request("/company/sessionPracticum/list", param);
//				super.checkPanicExists();
//				super.signOut();
//			}
//	}
//
//}
