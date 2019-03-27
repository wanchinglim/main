package seedu.address.model.subject;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.tag.SubjectTag;

/**
 * Unmodifiable view of an subject book
 */
public interface ReadOnlySubjectBook extends Observable {

    /**
     * Returns an unmodifiable view of the subjects list.
     * This list will not contain any duplicate subjects.
     */
    ObservableList<SubjectTag> getSubjectList();

}
