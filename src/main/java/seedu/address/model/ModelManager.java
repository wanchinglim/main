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
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.exceptions.SubjectNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Subject> filteredSubjects;
    private final SimpleObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredSubjects = new FilteredList<>(versionedAddressBook.getSubjectList());
        filteredSubjects.addListener(this::ensureSelectedSubjectIsValid);
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
    public boolean hasSubject(Subject subject) {
        requireNonNull(subject);
        return versionedAddressBook.hasSubject(subject);
    }

    @Override
    public void deleteSubject(Subject target) {
        versionedAddressBook.removeSubject(target);
    }

    @Override
    public void addSubject(Subject subject) {
        versionedAddressBook.addSubject(subject);
        updateFilteredSubjectList(PREDICATE_SHOW_ALL_SUBJECTS);
    }

    @Override
    public void setSubject(Subject target, Subject editedSubject) {
        requireAllNonNull(target, editedSubject);

        versionedAddressBook.setSubject(target, editedSubject);
    }

    //=========== Filtered Subject List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Subject} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Subject> getFilteredSubjectList() {
        return filteredSubjects;
    }

    @Override
    public void updateFilteredSubjectList(Predicate<Subject> predicate) {
        requireNonNull(predicate);
        filteredSubjects.setPredicate(predicate);
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

    //=========== Selected subject ===========================================================================

    @Override
    public ReadOnlyProperty<Subject> selectedSubjectProperty() {
        return selectedSubject;
    }

    @Override
    public Subject getSelectedSubject() {
        return selectedSubject.getValue();
    }

    @Override
    public void setSelectedSubject(Subject subject) {
        if (subject != null && !filteredSubjects.contains(subject)) {
            throw new SubjectNotFoundException();
        }
        selectedSubject.setValue(subject);
    }

    /**
     * Ensures {@code selectedSubject} is a valid subject in {@code filteredSubjects}.
     */
    private void ensureSelectedSubjectIsValid(ListChangeListener.Change<? extends Subject> change) {
        while (change.next()) {
            if (selectedSubject.getValue() == null) {
                // null is always a valid selected subject, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedSubjectReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedSubject.getValue());
            if (wasSelectedSubjectReplaced) {
                // Update selectedSubject to its new value.
                int index = change.getRemoved().indexOf(selectedSubject.getValue());
                selectedSubject.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedSubjectRemoved = change.getRemoved().stream()
                    .anyMatch(removedSubject -> selectedSubject.getValue().isSameSubject(removedSubject));
            if (wasSelectedSubjectRemoved) {
                // Select the subject that came before it in the list,
                // or clear the selection if there is no such subject.
                selectedSubject.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && filteredSubjects.equals(other.filteredSubjects)
                && Objects.equals(selectedSubject.get(), other.selectedSubject.get());
    }

}
