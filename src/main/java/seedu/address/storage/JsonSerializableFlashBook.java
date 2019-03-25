package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FlashBook;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.flashcard.Flashcard;

/**
 * An Immutable FlashBook that is serializable to JSON format.
 */
@JsonRootName(value = "flashbook")
class JsonSerializableFlashBook {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcards list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFlashBook} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableFlashBook(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyFlashBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFlashBook}.
     */
    public JsonSerializableFlashBook(ReadOnlyFlashBook source) {
        flashcards.addAll(source.getFlashcardList().stream().map(JsonAdaptedFlashcard::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this flash book into the model's {@code FlashBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FlashBook toModelType() throws IllegalValueException {
        FlashBook flashBook = new FlashBook();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (flashBook.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            flashBook.addFlashcard(flashcard);
        }
        return flashBook;
    }

}
