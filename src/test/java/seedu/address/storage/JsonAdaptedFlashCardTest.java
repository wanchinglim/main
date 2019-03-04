package seedu.address.storage;

//import static org.junit.Assert.assertEquals;
//import static seedu.address.storage.JsonAdaptedFlashCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalFlashCards.CHEMISTRY;
//import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

//import org.junit.Test;

//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.flashcard.Content;
//import seedu.address.model.flashcard.Subject;
//import seedu.address.testutil.Assert;

public class JsonAdaptedFlashCardTest {
    private static final String INVALID_SUBJECT = "R@chel";
    private static final String INVALID_CONTENT = "+651234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_SUBJECT = CHEMISTRY.getSubject().toString();
    private static final String VALID_CONTENT = CHEMISTRY.getContent().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CHEMISTRY.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    /**
    @Test
    public void toModelType_validFlashCardDetails_returnsFlashCard() throws Exception {
        JsonAdaptedFlashCard flashcard = new JsonAdaptedFlashCard(CHEMISTRY);
        assertEquals(CHEMISTRY, flashcard.toModelType());
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        JsonAdaptedFlashCard flashcard =
                new JsonAdaptedFlashCard(INVALID_SUBJECT, VALID_CONTENT, VALID_TAGS);
        String expectedMessage = Subject.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedFlashCard flashcard = new JsonAdaptedFlashCard(null, VALID_CONTENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidContent_throwsIllegalValueException() {
        JsonAdaptedFlashCard flashcard =
                new JsonAdaptedFlashCard(VALID_SUBJECT, INVALID_CONTENT, VALID_TAGS);
        String expectedMessage = Content.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullContent_throwsIllegalValueException() {
        JsonAdaptedFlashCard flashcard = new JsonAdaptedFlashCard(VALID_SUBJECT, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFlashCard flashcard =
                new JsonAdaptedFlashCard(VALID_SUBJECT, VALID_CONTENT, invalidTags);
        Assert.assertThrows(IllegalValueException.class, flashcard::toModelType);
    }**/

}
