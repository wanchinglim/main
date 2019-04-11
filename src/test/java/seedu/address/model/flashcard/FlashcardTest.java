package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_CHINESE;
import static seedu.address.testutil.TypicalFlashcards.CHINESE;
import static seedu.address.testutil.TypicalFlashcards.ENGLISH;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.FlashcardBuilder;

public class FlashcardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Flashcard flashcard = new FlashcardBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        flashcard.getTags().remove(0);
    }

    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(ENGLISH.isSameFlashcard(ENGLISH));

        // null -> returns false
        assertFalse(ENGLISH.isSameFlashcard(null));

        // different content -> returns false
        Flashcard editedEnglish =
                new FlashcardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        assertFalse(ENGLISH.isSameFlashcard(editedEnglish));

        // different topic -> returns false
        editedEnglish = new FlashcardBuilder(ENGLISH).withTopic(VALID_TOPIC_CHINESE).build();
        assertFalse(ENGLISH.isSameFlashcard(editedEnglish));

        // same topic, same difficulty, different attributes -> returns false
        editedEnglish = new FlashcardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH).build();
        assertFalse(ENGLISH.isSameFlashcard(editedEnglish));

        // same topic, same difficulty, different attributes -> returns false
        editedEnglish = new FlashcardBuilder(ENGLISH)
                .withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH)
                .build();
        assertFalse(ENGLISH.isSameFlashcard(editedEnglish));

        // same topic, same content, different attributes -> returns true
        editedEnglish = new FlashcardBuilder(ENGLISH)
                .withContent(VALID_CONTENT_ENGLISH)
                .withTags(VALID_TAG_CHINESE)
                .build();
        assertTrue(ENGLISH.isSameFlashcard(editedEnglish));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Flashcard englishCopy = new FlashcardBuilder(ENGLISH).build();
        assertTrue(ENGLISH.equals(englishCopy));

        // same object -> returns true
        assertTrue(ENGLISH.equals(ENGLISH));

        // null -> returns false
        assertFalse(ENGLISH.equals(null));

        // different type -> returns false
        assertFalse(ENGLISH.equals(5));

        // different flashcard -> returns false
        assertFalse(ENGLISH.equals(CHINESE));

        // different topic -> returns false
        Flashcard editedEnglish = new FlashcardBuilder(ENGLISH).withTopic(VALID_TOPIC_CHINESE).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different difficulty -> returns false
        editedEnglish = new FlashcardBuilder(ENGLISH).withDifficulty(VALID_DIFFICULTY_CHINESE).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different content -> returns false
        editedEnglish = new FlashcardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different tags -> returns false
        editedEnglish = new FlashcardBuilder(ENGLISH).withTags(VALID_TAG_CHINESE).build();
        assertFalse(ENGLISH.equals(editedEnglish));
    }
}
