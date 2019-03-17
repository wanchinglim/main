package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.subject.Subject;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the subjects list.
     * This list will not contain any duplicate subjects.
     */
    ObservableList<Subject> getSubjectList();

}
