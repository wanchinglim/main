package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.subject.Subject;

/**
 * Provides a handle for {@code SubjectListPanel} containing the list of {@code SubjectCard}.
 */
public class SubjectListPanelHandle extends NodeHandle<ListView<Subject>> {
    public static final String SUBJECT_LIST_VIEW_ID = "#subjectListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Subject> lastRememberedSelectedSubjectCard;

    public SubjectListPanelHandle(ListView<Subject> subjectListPanelNode) {
        super(subjectListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code SubjectCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public SubjectCardHandle getHandleToSelectedCard() {
        List<Subject> selectedSubjectList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedSubjectList.size() != 1) {
            throw new AssertionError("Subject list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(SubjectCardHandle::new)
                .filter(handle -> handle.equals(selectedSubjectList.get(0)))
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
        List<Subject> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code subject}.
     */
    public void navigateToCard(Subject subject) {
        if (!getRootNode().getItems().contains(subject)) {
            throw new IllegalArgumentException("Subject does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(subject);
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
     * Selects the {@code SubjectCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the subject card handle of a subject associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public SubjectCardHandle getSubjectCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(SubjectCardHandle::new)
                .filter(handle -> handle.equals(getSubject(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Subject getSubject(int index) {
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
     * Remembers the selected {@code SubjectCard} in the list.
     */
    public void rememberSelectedSubjectCard() {
        List<Subject> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedSubjectCard = Optional.empty();
        } else {
            lastRememberedSelectedSubjectCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code SubjectCard} is different from the value remembered by the most recent
     * {@code rememberSelectedSubjectCard()} call.
     */
    public boolean isSelectedSubjectCardChanged() {
        List<Subject> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedSubjectCard.isPresent();
        } else {
            return !lastRememberedSelectedSubjectCard.isPresent()
                    || !lastRememberedSelectedSubjectCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
