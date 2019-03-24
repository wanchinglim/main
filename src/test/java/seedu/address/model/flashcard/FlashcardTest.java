package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;
import static seedu.address.testutil.TypicalFlashcards.ALICE;
import static seedu.address.testutil.TypicalFlashcards.BOB;

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
        assertTrue(ALICE.isSameFlashcard(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameFlashcard(null));

        // different difficulty -> returns false
        Flashcard editedAlice =
                new FlashcardBuilder(ALICE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        assertFalse(ALICE.isSameFlashcard(editedAlice));

        // different topic -> returns false
        editedAlice = new FlashcardBuilder(ALICE).withTopic(VALID_TOPIC_BOB).build();
        assertFalse(ALICE.isSameFlashcard(editedAlice));

        // same topic, same difficulty, different attributes -> returns true
        editedAlice = new FlashcardBuilder(ALICE).withContent(VALID_CONTENT_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameFlashcard(editedAlice));

        // same topic, same difficulty, different attributes -> returns true
        editedAlice = new FlashcardBuilder(ALICE).withContent(VALID_CONTENT_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameFlashcard(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Flashcard aliceCopy = new FlashcardBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different flashcard -> returns false
        assertFalse(ALICE.equals(BOB));

        // different topic -> returns false
        Flashcard editedAlice = new FlashcardBuilder(ALICE).withTopic(VALID_TOPIC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different difficulty -> returns false
        editedAlice = new FlashcardBuilder(ALICE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different content -> returns false
        editedAlice = new FlashcardBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FlashcardBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
