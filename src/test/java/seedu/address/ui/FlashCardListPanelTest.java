package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalFlashCards.getTypicalFlashCards;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysFlashCard;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.FlashCardCardHandle;
import guitests.guihandles.FlashCardListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;

public class FlashCardListPanelTest extends GuiUnitTest {
    private static final ObservableList<FlashCard> TYPICAL_FLASHCARDS =
            FXCollections.observableList(getTypicalFlashCards());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<FlashCard> selectedFlashCard = new SimpleObjectProperty<>();
    private FlashCardListPanelHandle flashcardListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_FLASHCARDS);

        for (int i = 0; i < TYPICAL_FLASHCARDS.size(); i++) {
            flashcardListPanelHandle.navigateToCard(TYPICAL_FLASHCARDS.get(i));
            FlashCard expectedFlashCard = TYPICAL_FLASHCARDS.get(i);
            FlashCardCardHandle actualCard = flashcardListPanelHandle.getFlashCardCardHandle(i);

            assertCardDisplaysFlashCard(expectedFlashCard, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedFlashCardChanged_selectionChanges() {
        initUi(TYPICAL_FLASHCARDS);
        FlashCard secondFlashCard = TYPICAL_FLASHCARDS.get(INDEX_SECOND_FLASHCARD.getZeroBased());
        guiRobot.interact(() -> selectedFlashCard.set(secondFlashCard));
        guiRobot.pauseForHuman();

        FlashCardCardHandle expectedFlashCard = flashcardListPanelHandle
                .getFlashCardCardHandle(INDEX_SECOND_FLASHCARD.getZeroBased());
        FlashCardCardHandle selectedFlashCard = flashcardListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedFlashCard, selectedFlashCard);
    }

    /**
     * Verifies that creating and deleting large number of flashcards in {@code FlashCardListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */

    @Test
    public void performanceTest() {
        ObservableList<FlashCard> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of flashcard cards exceeded time limit");
    }

    /**
     * Returns a list of flashcards containing {@code flashcardCount} flashcards that is used to populate the
     * {@code FlashCardListPanel}.
     */
    private ObservableList<FlashCard> createBackingList(int flashcardCount) {
        ObservableList<FlashCard> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < flashcardCount; i++) {
            Subject name = new Subject(i + "a");
            Content phone = new Content("000");
            FlashCard flashcard = new FlashCard(name, phone, Collections.emptySet());
            backingList.add(flashcard);
        }
        return backingList;
    }

    /**
     * Initializes {@code flashcardListPanelHandle} with a {@code FlashCardListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code FlashCardListPanel}.
     */
    private void initUi(ObservableList<FlashCard> backingList) {
        FlashCardListPanel flashcardListPanel =
                new FlashCardListPanel(backingList, selectedFlashCard, selectedFlashCard::set);
        uiPartRule.setUiPart(flashcardListPanel);

        flashcardListPanelHandle = new FlashCardListPanelHandle(getChildNode(flashcardListPanel.getRoot(),
                FlashCardListPanelHandle.FLASHCARD_LIST_VIEW_ID));
    }
}
