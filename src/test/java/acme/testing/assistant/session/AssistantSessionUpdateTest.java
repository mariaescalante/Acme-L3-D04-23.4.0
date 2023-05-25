
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Session;
import acme.testing.TestHarness;

public class AssistantSessionUpdateTest extends TestHarness {

	@Autowired
	protected AssistantSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final int sessionRecordIndex, final String title, final String abstract$, final String indication, final String startTime, final String endTime, final String link) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkFormExists();
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(sessionRecordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(sessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("link", link);

		super.signOut();
	}
	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final int sessionRecordIndex, final String title, final String abstract$, final String indication, final String startTime, final String endTime, final String link) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkFormExists();
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(sessionRecordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Session> sessions;
		String param;

		sessions = this.repository.findManySessionsByAssistantUsername("assistant1");
		for (final Session session : sessions) {
			param = String.format("id=%d", session.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/session/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
