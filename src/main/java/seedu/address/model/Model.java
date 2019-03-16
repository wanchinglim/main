package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.subject.Subject;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Subject> PREDICATE_SHOW_ALL_SUBJECTS = unused -> true;

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
     * Returns true if a subject with the same identity as {@code subject} exists in the address book.
     */
    boolean hasSubject(Subject subject);

    /**
     * Deletes the given subject.
     * The subject must exist in the address book.
     */
    void deleteSubject(Subject target);

    /**
     * Adds the given subject.
     * {@code subject} must not already exist in the address book.
     */
    void addSubject(Subject subject);

    /**
     * Replaces the given subject {@code target} with {@code editedSubject}.
     * {@code target} must exist in the address book.
     * The subject identity of {@code editedSubject} must not
     * be the same as another existing subject in the address book.
     */
    void setSubject(Subject target, Subject editedSubject);

    /** Returns an unmodifiable view of the filtered subject list */
    ObservableList<Subject> getFilteredSubjectList();

    /**
     * Updates the filter of the filtered subject list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSubjectList(Predicate<Subject> predicate);

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
     * Selected subject in the filtered subject list.
     * null if no subject is selected.
     */
    ReadOnlyProperty<Subject> selectedSubjectProperty();

    /**
     * Returns the selected subject in the filtered subject list.
     * null if no subject is selected.
     */
    Subject getSelectedSubject();

    /**
     * Sets the selected subject in the filtered subject list.
     */
    void setSelectedSubject(Subject subject);
}
