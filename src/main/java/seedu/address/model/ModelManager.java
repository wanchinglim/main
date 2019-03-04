package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<FlashCard> filteredFlashCards;
    private final SimpleObjectProperty<FlashCard> selectedFlashCard = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashCards = new FilteredList<>(versionedAddressBook.getFlashCardList());
        filteredFlashCards.addListener(this::ensureSelectedFlashCardIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasFlashCard(FlashCard flashcard) {
        requireNonNull(flashcard);
        return versionedAddressBook.hasFlashCard(flashcard);
    }

    @Override
    public void deleteFlashCard(FlashCard target) {
        versionedAddressBook.removeFlashCard(target);
    }

    @Override
    public void addFlashCard(FlashCard flashcard) {
        versionedAddressBook.addFlashCard(flashcard);
        updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashCard(FlashCard target, FlashCard editedFlashCard) {
        requireAllNonNull(target, editedFlashCard);

        versionedAddressBook.setFlashCard(target, editedFlashCard);
    }

    //=========== Filtered FlashCard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code FlashCard} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<FlashCard> getFilteredFlashCardList() {
        return filteredFlashCards;
    }

    @Override
    public void updateFilteredFlashCardList(Predicate<FlashCard> predicate) {
        requireNonNull(predicate);
        filteredFlashCards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected flashcard ===========================================================================

    @Override
    public ReadOnlyProperty<FlashCard> selectedFlashCardProperty() {
        return selectedFlashCard;
    }

    @Override
    public FlashCard getSelectedFlashCard() {
        return selectedFlashCard.getValue();
    }

    @Override
    public void setSelectedFlashCard(FlashCard flashcard) {
        if (flashcard != null && !filteredFlashCards.contains(flashcard)) {
            throw new FlashCardNotFoundException();
        }
        selectedFlashCard.setValue(flashcard);
    }

    /**
     * Ensures {@code selectedFlashCard} is a valid flashcard in {@code filteredFlashCards}.
     */
    private void ensureSelectedFlashCardIsValid(ListChangeListener.Change<? extends FlashCard> change) {
        while (change.next()) {
            if (selectedFlashCard.getValue() == null) {
                // null is always a valid selected flashcard, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedFlashCardReplaced = change.wasReplaced()
                && change.getAddedSize() == change.getRemovedSize()
                && change.getRemoved().contains(selectedFlashCard.getValue());
            if (wasSelectedFlashCardReplaced) {
                // Update selectedFlashCard to its new value.
                int index = change.getRemoved().indexOf(selectedFlashCard.getValue());
                selectedFlashCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedFlashCardRemoved = change.getRemoved().stream()
                    .anyMatch(removedFlashCard -> selectedFlashCard.getValue().isSameFlashCard(removedFlashCard));
            if (wasSelectedFlashCardRemoved) {
                // Select the flashcard that came before it in the list,
                // or clear the selection if there is no such flashcard.
                selectedFlashCard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashCards.equals(other.filteredFlashCards)
                && Objects.equals(selectedFlashCard.get(), other.selectedFlashCard.get());
    }

}
