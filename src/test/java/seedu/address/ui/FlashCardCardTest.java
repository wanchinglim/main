package seedu.address.ui;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysFlashCard;

//import org.junit.Test;

import guitests.guihandles.FlashCardCardHandle;
import seedu.address.model.flashcard.FlashCard;
//import seedu.address.testutil.FlashCardBuilder;

public class FlashCardCardTest extends GuiUnitTest {
    /**
    @Test
    public void display() {
        // no tags
        FlashCard flashcardWithNoTags = new FlashCardBuilder().withTags(new String[0]).build();
        FlashCardCard flashcardCard = new FlashCardCard(flashcardWithNoTags, 1);
        uiPartRule.setUiPart(flashcardCard);
        assertCardDisplay(flashcardCard, flashcardWithNoTags, 1);

        // with tags
        FlashCard flashcardWithTags = new FlashCardBuilder().build();
        flashcardCard = new FlashCardCard(flashcardWithTags, 2);
        uiPartRule.setUiPart(flashcardCard);
        assertCardDisplay(flashcardCard, flashcardWithTags, 2);
    }

    @Test
    public void equals() {
        FlashCard flashcard = new FlashCardBuilder().build();
        FlashCardCard flashcardCard = new FlashCardCard(flashcard, 0);

        // same flashcard, same index -> returns true
        FlashCardCard copy = new FlashCardCard(flashcard, 0);
        assertTrue(flashcardCard.equals(copy));

        // same object -> returns true
        assertTrue(flashcardCard.equals(flashcardCard));

        // null -> returns false
        assertFalse(flashcardCard.equals(null));

        // different types -> returns false
        assertFalse(flashcardCard.equals(0));

        // different flashcard, same index -> returns false
        FlashCard differentFlashCard = new FlashCardBuilder().withSubject("differentSubject").build();
        assertFalse(flashcardCard.equals(new FlashCardCard(differentFlashCard, 0)));

        // same flashcard, different index -> returns false
        assertFalse(flashcardCard.equals(new FlashCardCard(flashcard, 1)));
    }**/

    /**
     * Asserts that {@code flashcardCard} displays the details of {@code expectedFlashCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(FlashCardCard flashcardCard, FlashCard expectedFlashCard, int expectedId) {
        guiRobot.pauseForHuman();

        FlashCardCardHandle flashcardCardHandle = new FlashCardCardHandle(flashcardCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", flashcardCardHandle.getId());

        // verify flashcard details are displayed correctly
        assertCardDisplaysFlashCard(expectedFlashCard, flashcardCardHandle);
    }
}
