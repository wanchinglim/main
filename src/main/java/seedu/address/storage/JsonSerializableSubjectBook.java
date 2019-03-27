package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

/**
 * An Immutable SubjectBook that is serializable to JSON format.
 */
@JsonRootName(value = "subjectbook")
public class JsonSerializableSubjectBook {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Subject list contains duplicate flashcard(s).";

    private final List<JsonAdaptedSubjectBook> subjects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFlashBook} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableSubjectBook(@JsonProperty("subjectbook") List<JsonAdaptedSubjectBook> subjects) {
        this.subjects.addAll(subjects);
    }

    /**
     * Converts a given {@code ReadOnlyFlashBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFlashBook}.
     */
    public JsonSerializableSubjectBook(ReadOnlySubjectBook source) {
        subjects.addAll(source.getSubjectList().stream().map(JsonAdaptedSubjectBook::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this flash book into the model's {@code FlashBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SubjectBook toModelType() throws IllegalValueException {
        SubjectBook subjectBook = new SubjectBook();
        for (JsonAdaptedSubjectBook jsonAdaptedSubjectBook : subjects) {
            SubjectTag sub = jsonAdaptedSubjectBook.toModelType();
            if (subjectBook.hasSubject(sub)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            subjectBook.addSubject(sub);
        }
        return subjectBook;
    }

}
