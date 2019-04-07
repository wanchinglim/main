package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.tag.SubjectTag;

/**
 * Wraps all data at the subject-book level
 * Duplicates are not allowed (by .isSameSubject comparison)
 */
public class SubjectBook implements ReadOnlySubjectBook {

    private final UniqueSubjectList subjects;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    private SubjectTag subjectName;

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

    public SubjectBook() {}

    /**
     * Creates an SubjectBook using the Flashcards in the {@code subject}
     */
    public SubjectBook(SubjectTag subjectTag) {
        this();
        this.subjectName = subjectTag;
        subjects.add(subjectTag);
    }

    /**
     * Creates an FlashBook using the Flashcards in the {@code toBeCopied}
     */
    public SubjectBook(ReadOnlySubjectBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code SubjectBook} with {@code newData}.
     */
    public void resetData(ReadOnlySubjectBook newData) {
        requireNonNull(newData);
        setSubjects(newData.getSubjectList());
    }

    //// flashcard-level operations

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the flash book.
     */
    public boolean hasSubject(SubjectTag subject) {
        requireNonNull(subject);
        return subjects.contains(subject);
    }

    /**
     * Adds a subject to the subject book.
     * The subject must not already exist in the subject book.
     */
    public void addSubject(SubjectTag subject) {
        subjects.add(subject);
        indicateModified();
    }

    /**
     * Deletes a subject to the subject book.
     */
    public void deleteSubject(SubjectTag subject) {
        subjects.remove(subject);
        indicateModified();
    }

    /**
     * Replaces the contents of the flashcard list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    private void setSubjects(ObservableList<SubjectTag> subjectList) {
        this.subjects.setSubjects(subjectList);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code SubjectBook}.
     * {@code key} must exist in the flash book.
     */
    public void removeSubject(SubjectTag key) {
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
     * Notifies listeners that the flash book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    public String getSubject() {
        return subjectName.toString();
    }

    @Override
    public String toString() {
        return subjects.asUnmodifiableObservableList().size() + " subjects";
        // TODO: refine later
    }

    @Override
    public ObservableList<SubjectTag> getSubjectList() {
        return subjects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubjectBook // instanceof handles nulls
                && subjects.equals(((SubjectBook) other).subjects));
    }

    @Override
    public int hashCode() {
        return subjects.hashCode();
    }
}
