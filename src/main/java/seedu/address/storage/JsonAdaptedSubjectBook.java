package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

import javax.security.auth.Subject;

/**
 * Jackson-friendly version of {@link SubjectBook}.
 */
public class JsonAdaptedSubjectBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedSubjectBook(@JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedSubjectBook(SubjectBook source) {
        tagged.addAll(source.getSubjectList().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    public JsonAdaptedSubjectBook(SubjectTag subjectTag) {

    }


    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public SubjectTag toModelType() throws IllegalValueException {
        final List<SubjectTag> flashcardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            flashcardTags.add(tag.toModelType());
        }

        final Set<SubjectTag> modelTags = new HashSet<>(flashcardTags);
        return new SubjectTag(modelTags.toString());
    }

}
