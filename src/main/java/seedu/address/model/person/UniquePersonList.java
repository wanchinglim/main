package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A subject is considered unique by comparing using {@code Subject#isSamePerson(Subject)}.
 * As such, adding and updating of persons uses Subject#isSamePerson(Subject) for equality so as to
 * ensure that the subject being added or updated is unique in terms of identity in the UniquePersonList.
 * However, the removal of a subject uses Subject#equals(Object) so
 * as to ensure that the subject with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Subject#isSamePerson(Subject)
 */
public class UniquePersonList implements Iterable<Subject> {

    private final ObservableList<Subject> internalList = FXCollections.observableArrayList();
    private final ObservableList<Subject> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent subject as the given argument.
     */
    public boolean contains(Subject toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a subject to the list.
     * The subject must not already exist in the list.
     */
    public void add(Subject toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the subject {@code target} in the list with {@code editedSubject}.
     * {@code target} must exist in the list.
     * The subject identity of {@code editedSubject} must not be the same as another existing subject in the list.
     */
    public void setPerson(Subject target, Subject editedSubject) {
        requireAllNonNull(target, editedSubject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedSubject) && contains(editedSubject)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedSubject);
    }

    /**
     * Removes the equivalent subject from the list.
     * The subject must exist in the list.
     */
    public void remove(Subject toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code subjects}.
     * {@code subjects} must not contain duplicate subjects.
     */
    public void setPersons(List<Subject> subjects) {
        requireAllNonNull(subjects);
        if (!personsAreUnique(subjects)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(subjects);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subject> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Subject> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code subjects} contains only unique subjects.
     */
    private boolean personsAreUnique(List<Subject> subjects) {
        for (int i = 0; i < subjects.size() - 1; i++) {
            for (int j = i + 1; j < subjects.size(); j++) {
                if (subjects.get(i).isSamePerson(subjects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
