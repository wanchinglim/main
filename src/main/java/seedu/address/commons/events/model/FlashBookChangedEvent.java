package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyFlashBook;

/** Indicates the FlashBook in the model has changed*/
public class FlashBookChangedEvent extends BaseEvent {

    public final ReadOnlyFlashBook data;

    public FlashBookChangedEvent(ReadOnlyFlashBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of flashcards " + data.getFlashcardList().size();
    }
}
