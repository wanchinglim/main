package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.flashcard.FlashCard;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "FlashCards list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashCard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("flashcards") List<JsonAdaptedFlashCard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        flashcards.addAll(source.getFlashCardList().stream()
            .map(JsonAdaptedFlashCard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedFlashCard jsonAdaptedFlashCard : flashcards) {
            FlashCard flashcard = jsonAdaptedFlashCard.toModelType();
            if (addressBook.hasFlashCard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            addressBook.addFlashCard(flashcard);
        }
        return addressBook;
    }

}
