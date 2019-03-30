package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;

import seedu.address.model.tag.SubjectTag;

/**
 * A list of subjects that enforces uniqueness between its elements and does not allow nulls.
 * A subject is considered unique by comparing using {@code SubjectBook#isSameSubject(SubjectBook)}.
 * As such, adding and updating of subjects uses SubjectBook#isSameSubject(SubjectBook) for equality so as to
 * ensure that the subject being added or updated is unique in terms of identity in the UniqueSubjectList.
 * However, the removal of a subject uses SubjectBook#equals(Object) so
 * as to ensure that the subject with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see SubjectTag#isSameSubject(SubjectTag)
 */
public class UniqueSubjectList implements Iterable<SubjectTag> {

    private final ObservableList<SubjectTag> internalList = FXCollections.observableArrayList();
    private final ObservableList<SubjectTag> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent subject as the given argument.
     */
    public boolean contains(SubjectTag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSubject);
    }

    /**
     * Adds a subject to the list.
     * The subject must not already exist in the list.
     */
    public void add(SubjectTag toAdd) {
        requireNonNull(toAdd);

        if (!internalList.contains(toAdd)) {
            internalList.add(toAdd);
        }

    }

    /**
     * Replaces the subject {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the list.
     * The subject identity of {@code editedFlashcard} must not be the same as another existing subject in the list.
     */
    public void setSubject(SubjectTag target, SubjectTag editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FlashcardNotFoundException();
        }

        if (!target.isSameSubject(editedFlashcard) && contains(editedFlashcard)) {
            throw new DuplicateFlashcardException();
        }

        internalList.set(index, editedFlashcard);
    }

    /**
     * Removes the equivalent subject from the list.
     * The subject must exist in the list.
     */
    public void remove(SubjectTag toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FlashcardNotFoundException();
        }
    }

    public void setSubjects(UniqueSubjectList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code subjects}.
     * {@code subjects} must not contain duplicate subjects.
     */
    public void setSubjects(List<SubjectTag> subjects) {
        requireAllNonNull(subjects);
        if (!subjectsAreUnique(subjects)) {
            throw new DuplicateFlashcardException();
        }

        internalList.setAll(subjects);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<SubjectTag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<SubjectTag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueSubjectList // instanceof handles nulls
                && internalList.equals(((UniqueSubjectList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code subjects} contains only unique subjects.
     */
    private boolean subjectsAreUnique(List<SubjectTag> subjects) {
        for (int i = 0; i < subjects.size() - 1; i++) {
            for (int j = i + 1; j < subjects.size(); j++) {
                if (subjects.get(i).isSameSubject(subjects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
