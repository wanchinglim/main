package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.FlashCardCardHandle;
import guitests.guihandles.FlashCardListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.flashcard.FlashCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(FlashCardCardHandle expectedCard, FlashCardCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getSubject(), actualCard.getSubject());
        assertEquals(expectedCard.getContent(), actualCard.getContent());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedFlashCard}.
     */
    public static void assertCardDisplaysFlashCard(FlashCard expectedFlashCard, FlashCardCardHandle actualCard) {
        assertEquals(expectedFlashCard.getSubject().subject, actualCard.getSubject());
        assertEquals(expectedFlashCard.getContent().content, actualCard.getContent());
        assertEquals(expectedFlashCard.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code flashcardListPanelHandle} displays the details of {@code flashcards}
     * correctly and in the correct order.
     */
    public static void assertListMatching(FlashCardListPanelHandle flashcardListPanelHandle, FlashCard... flashcards) {
        for (int i = 0; i < flashcards.length; i++) {
            flashcardListPanelHandle.navigateToCard(i);
            assertCardDisplaysFlashCard(flashcards[i], flashcardListPanelHandle.getFlashCardCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code flashcardListPanelHandle} displays the details of {@code flashcards}
     * correctly and in the correct order.
     */
    public static void assertListMatching(FlashCardListPanelHandle flashcardListPanelHandle,
                                            List<FlashCard> flashcards) {
        assertListMatching(flashcardListPanelHandle, flashcards.toArray(new FlashCard[0]));
    }

    /**
     * Asserts the size of the list in {@code flashcardListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(FlashCardListPanelHandle flashcardListPanelHandle, int size) {
        int numberOfPeople = flashcardListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
