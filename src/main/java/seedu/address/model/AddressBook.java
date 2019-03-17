package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.UniqueSubjectList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameSubject comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueSubjectList subjects;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        subjects = new UniqueSubjectList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Subjects in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the subject list with {@code subjects}.
     * {@code subjects} must not contain duplicate subjects.
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects.setSubjects(subjects);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setSubjects(newData.getSubjectList());
    }

    //// subject-level operations

    /**
     * Returns true if a subject with the same identity as {@code subject} exists in the address book.
     */
    public boolean hasSubject(Subject subject) {
        requireNonNull(subject);
        return subjects.contains(subject);
    }

    /**
     * Adds a subject to the address book.
     * The subject must not already exist in the address book.
     */
    public void addSubject(Subject p) {
        subjects.add(p);
        indicateModified();
    }

    /**
     * Replaces the given subject {@code target} in the list with {@code editedSubject}.
     * {@code target} must exist in the address book.
     * The subject identity of {@code editedSubject} must not be the same as another
     * existing subject in the address book.
     */
    public void setSubject(Subject target, Subject editedSubject) {
        requireNonNull(editedSubject);

        subjects.setSubject(target, editedSubject);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeSubject(Subject key) {
        subjects.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return subjects.asUnmodifiableObservableList().size() + " subjects";
        // TODO: refine later
    }

    @Override
    public ObservableList<Subject> getSubjectList() {
        return subjects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && subjects.equals(((AddressBook) other).subjects));
    }

    @Override
    public int hashCode() {
        return subjects.hashCode();
    }
}
