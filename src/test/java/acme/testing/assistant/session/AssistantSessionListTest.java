
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantSessionListTest extends TestHarness {

	@Autowired
	protected AssistantSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String titleTutorial, final int sessionRecordIndex, final String title, final String abstract$, final String indication) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, titleTutorial);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("title", titleTutorial);
		super.clickOnButton("Sessions");

		super.checkListingExists();
		super.checkColumnHasValue(sessionRecordIndex, 0, title);
		super.checkColumnHasValue(sessionRecordIndex, 1, abstract$);
		super.checkColumnHasValue(sessionRecordIndex, 2, indication);
		super.clickOnListingRecord(sessionRecordIndex);

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("masterId=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/session/list", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/activity/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
