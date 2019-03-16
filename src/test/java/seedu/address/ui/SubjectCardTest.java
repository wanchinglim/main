package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.PersonBuilder;

public class SubjectCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Subject subjectWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(subjectWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, subjectWithNoTags, 1);

        // with tags
        Subject subjectWithTags = new PersonBuilder().build();
        personCard = new PersonCard(subjectWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, subjectWithTags, 2);
    }

    @Test
    public void equals() {
        Subject subject = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(subject, 0);

        // same subject, same index -> returns true
        PersonCard copy = new PersonCard(subject, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different subject, same index -> returns false
        Subject differentSubject = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentSubject, 0)));

        // same subject, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(subject, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedSubject} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Subject expectedSubject, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify subject details are displayed correctly
        assertCardDisplaysPerson(expectedSubject, personCardHandle);
    }
}
