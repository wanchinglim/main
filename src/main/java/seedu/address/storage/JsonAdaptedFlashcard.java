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
import seedu.address.model.tag.Subject;

/**
 * Jackson-friendly version of {@link Flashcard}.
 */
class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";

    private final String topic;
    private final String difficulty;
    private final String content;
    private final String deadline;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("topic") String topic, @JsonProperty("difficulty") String difficulty,
                                @JsonProperty("content") String content,
                                @JsonProperty("deadline") String deadline,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.content = content;
        this.deadline = deadline;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        topic = source.getTopic().fullTopic;
        difficulty = source.getDifficulty().value;
        content = source.getContent().value;
        deadline = source.getDeadline().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Flashcard toModelType() throws IllegalValueException {
        final List<Subject> flashcardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            flashcardTags.add(tag.toModelType());
        }

        if (topic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Topic.class.getSimpleName()));
        }
        if (!Topic.isValidTopic(topic)) {
            throw new IllegalValueException(Topic.MESSAGE_CONSTRAINTS);
        }
        final Topic modelTopic = new Topic(topic);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }
        final Content modelContent = new Content(content);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));

        }
        final Deadline modelDeadline = new Deadline(deadline);
        final Set<Subject> modelTags = new HashSet<>(flashcardTags);
        return new Flashcard(modelTopic, modelDifficulty, modelContent, modelDeadline, modelTags);
    }

}
