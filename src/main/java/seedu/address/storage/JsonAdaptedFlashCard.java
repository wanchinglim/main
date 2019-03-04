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
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link FlashCard}.
 */
class JsonAdaptedFlashCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "FlashCard's %s field is missing!";

    private final String sub;
    private final String cont;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashCard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashCard(@JsonProperty("sub") String sub, @JsonProperty("cont") String cont,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.sub = sub;
        this.cont = cont;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code FlashCard} into this class for Jackson use.
     */
    public JsonAdaptedFlashCard(FlashCard source) {
        sub = source.getSubject().subject;
        cont = source.getContent().content;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code FlashCard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public FlashCard toModelType() throws IllegalValueException {
        final List<Tag> flashcardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            flashcardTags.add(tag.toModelType());
        }

        if (sub == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(sub)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(sub);

        if (cont == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(cont)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }
        final Content modelContent = new Content(cont);


        final Set<Tag> modelTags = new HashSet<>(flashcardTags);
        return new FlashCard(modelSubject, modelContent, modelTags);
    }

}
