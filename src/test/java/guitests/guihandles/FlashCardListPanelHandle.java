package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.flashcard.FlashCard;

/**
 * Provides a handle for {@code FlashCardListPanel} containing the list of {@code FlashCardCard}.
 */
public class FlashCardListPanelHandle extends NodeHandle<ListView<FlashCard>> {
    public static final String FLASHCARD_LIST_VIEW_ID = "#flashcardListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<FlashCard> lastRememberedSelectedFlashCardCard;

    public FlashCardListPanelHandle(ListView<FlashCard> flashcardListPanelNode) {
        super(flashcardListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code FlashCardCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public FlashCardCardHandle getHandleToSelectedCard() {
        List<FlashCard> selectedFlashCardList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedFlashCardList.size() != 1) {
            throw new AssertionError("FlashCard list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(FlashCardCardHandle::new)
                .filter(handle -> handle.equals(selectedFlashCardList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<FlashCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code flashcard}.
     */
    public void navigateToCard(FlashCard flashcard) {
        if (!getRootNode().getItems().contains(flashcard)) {
            throw new IllegalArgumentException("FlashCard does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(flashcard);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code FlashCardCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the flashcard card handle of a flashcard associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public FlashCardCardHandle getFlashCardCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(FlashCardCardHandle::new)
                .filter(handle -> handle.equals(getFlashCard(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private FlashCard getFlashCard(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code FlashCardCard} in the list.
     */
    public void rememberSelectedFlashCardCard() {
        List<FlashCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedFlashCardCard = Optional.empty();
        } else {
            lastRememberedSelectedFlashCardCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code FlashCardCard} is different from the value remembered by the most recent
     * {@code rememberSelectedFlashCardCard()} call.
     */
    public boolean isSelectedFlashCardCardChanged() {
        List<FlashCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedFlashCardCard.isPresent();
        } else {
            return !lastRememberedSelectedFlashCardCard.isPresent()
                    || !lastRememberedSelectedFlashCardCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
