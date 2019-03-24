package seedu.address.testutil;

import seedu.address.model.FlashBook;
import seedu.address.model.flashcard.Flashcard;

/**
 * A utility class to help with building Flashbook objects.
 * Example usage: <br>
 *     {@code FlashBook ab = new FlashBookBuilder().withFlashcard("John", "Doe").build();}
 */
public class FlashBookBuilder {

    private FlashBook flashBook;

    public FlashBookBuilder() {
        flashBook = new FlashBook();
    }

    public FlashBookBuilder(FlashBook flashBook) {
        this.flashBook = flashBook;
    }

    /**
     * Adds a new {@code Flashcard} to the {@code FlashBook} that we are building.
     */
    public FlashBookBuilder withFlashcard(Flashcard flashcard) {
        flashBook.addFlashcard(flashcard);
        return this;
    }

    public FlashBook build() {
        return flashBook;
    }
}
