package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalFlashcards.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Email;
import seedu.address.model.flashcard.Topic;
import seedu.address.testutil.Assert;

public class JsonAdaptedFlashcardTest {
    private static final String INVALID_TOPIC = "R@chel";
    private static final String INVALID_DIFFICULTY = "+651234";
    private static final String INVALID_CONTENT = " ";
    private static final String INVALID_EMAIL = "example.com";
    //private static final String INVALID_DEADLINE = "24@December_2019";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TOPIC = BENSON.getTopic().toString();
    private static final String VALID_DIFFICULTY = BENSON.getDifficulty().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_CONTENT = BENSON.getContent().toString();
    private static final String VALID_DEADLINE = BENSON.getDeadline().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard subject = new JsonAdaptedFlashcard(BENSON);
        assertEquals(BENSON, subject.toModelType());
    }

    @Test
    public void toModelType_invalidTopic_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject =
                new JsonAdaptedFlashcard(INVALID_TOPIC, VALID_DIFFICULTY, VALID_EMAIL, VALID_CONTENT,
                        VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = Topic.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_nullTopic_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject = new JsonAdaptedFlashcard(null, VALID_DIFFICULTY, VALID_EMAIL, VALID_CONTENT,
                VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Topic.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_invalidDifficulty_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject =
                new JsonAdaptedFlashcard(VALID_TOPIC, INVALID_DIFFICULTY, VALID_EMAIL, VALID_CONTENT,
                        VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = Difficulty.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_nullDifficulty_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject = new JsonAdaptedFlashcard(VALID_TOPIC, null, VALID_EMAIL, VALID_CONTENT,
                VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject =
                new JsonAdaptedFlashcard(VALID_TOPIC, VALID_DIFFICULTY, INVALID_EMAIL, VALID_CONTENT,
                        VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject = new JsonAdaptedFlashcard(VALID_TOPIC, VALID_DIFFICULTY, null, VALID_CONTENT,
                VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_invalidContent_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject =
                new JsonAdaptedFlashcard(VALID_TOPIC, VALID_DIFFICULTY, VALID_EMAIL, INVALID_CONTENT,
                        VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = Content.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_nullContent_throwsIllegalValueException() {
        JsonAdaptedFlashcard subject = new JsonAdaptedFlashcard(VALID_TOPIC, VALID_DIFFICULTY, VALID_EMAIL, null,
                VALID_DEADLINE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFlashcard subject =
                new JsonAdaptedFlashcard(VALID_TOPIC, VALID_DIFFICULTY, VALID_EMAIL, VALID_CONTENT,
                        VALID_DEADLINE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, subject::toModelType);
    }

}
