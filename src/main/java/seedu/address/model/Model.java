package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.FlashCard;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<FlashCard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the address book.
     */
    boolean hasFlashCard(FlashCard flashcard);

    /**
     * Deletes the given flashcard.
     * The flashcard must exist in the address book.
     */
    void deleteFlashCard(FlashCard target);

    /**
     * Adds the given flashcard.
     * {@code flashcard} must not already exist in the address book.
     */
    void addFlashCard(FlashCard flashcard);

    /**
     * Replaces the given flashcard {@code target} with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashcard identity of {@code editedFlashCard} must not be the
     * same as another existing flashcard in the address book.
     */
    void setFlashCard(FlashCard target, FlashCard editedFlashCard);

    /** Returns an unmodifiable view of the filtered flashcard list */
    ObservableList<FlashCard> getFilteredFlashCardList();

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashCardList(Predicate<FlashCard> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     */
    ReadOnlyProperty<FlashCard> selectedFlashCardProperty();

    /**
     * Returns the selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     */
    FlashCard getSelectedFlashCard();

    /**
     * Sets the selected flashcard in the filtered flashcard list.
     */
    void setSelectedFlashCard(FlashCard flashcard);
}
