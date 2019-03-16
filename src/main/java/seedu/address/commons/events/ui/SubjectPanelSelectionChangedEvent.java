package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.subject.Subject;

/**
 * Represents a selection change in the Subject List Panel
 */
public class SubjectPanelSelectionChangedEvent extends BaseEvent {


    private final Subject newSelection;

    public SubjectPanelSelectionChangedEvent(Subject newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Subject getNewSelection() {
        return newSelection;
    }
}
