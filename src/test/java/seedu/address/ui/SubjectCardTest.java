package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.SubjectBuilder;

public class SubjectCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Subject subjectWithNoTags = new SubjectBuilder().withTags(new String[0]).build();
        SubjectCard subjectCard = new SubjectCard(subjectWithNoTags, 1);
        uiPartRule.setUiPart(subjectCard);
        assertCardDisplay(subjectCard, subjectWithNoTags, 1);

        // with tags
        Subject subjectWithTags = new SubjectBuilder().build();
        subjectCard = new SubjectCard(subjectWithTags, 2);
        uiPartRule.setUiPart(subjectCard);
        assertCardDisplay(subjectCard, subjectWithTags, 2);
    }

    @Test
    public void equals() {
        Subject subject = new SubjectBuilder().build();
        SubjectCard subjectCard = new SubjectCard(subject, 0);

        // same subject, same index -> returns true
        SubjectCard copy = new SubjectCard(subject, 0);
        assertTrue(subjectCard.equals(copy));

        // same object -> returns true
        assertTrue(subjectCard.equals(subjectCard));

        // null -> returns false
        assertFalse(subjectCard.equals(null));

        // different types -> returns false
        assertFalse(subjectCard.equals(0));

        // different subject, same index -> returns false
        Subject differentSubject = new SubjectBuilder().withName("differentName").build();
        assertFalse(subjectCard.equals(new SubjectCard(differentSubject, 0)));

        // same subject, different index -> returns false
        assertFalse(subjectCard.equals(new SubjectCard(subject, 1)));
    }

    /**
     * Asserts that {@code subjectCard} displays the details of {@code expectedSubject} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(SubjectCard subjectCard, Subject expectedSubject, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(subjectCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify subject details are displayed correctly
        assertCardDisplaysPerson(expectedSubject, personCardHandle);
    }
}
