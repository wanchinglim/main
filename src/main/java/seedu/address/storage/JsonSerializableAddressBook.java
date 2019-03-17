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
import seedu.address.model.subject.Subject;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_SUBJECT = "Subjects list contains duplicate subject(s).";

    private final List<JsonAdaptedSubject> subjects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given subjects.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("subjects") List<JsonAdaptedSubject> subjects) {
        this.subjects.addAll(subjects);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        subjects.addAll(source.getSubjectList().stream().map(JsonAdaptedSubject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedSubject jsonAdaptedSubject : subjects) {
            Subject subject = jsonAdaptedSubject.toModelType();
            if (addressBook.hasSubject(subject)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SUBJECT);
            }
            addressBook.addSubject(subject);
        }
        return addressBook;
    }

}
