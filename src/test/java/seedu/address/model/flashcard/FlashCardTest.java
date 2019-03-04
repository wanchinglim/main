package seedu.address.model.flashcard;

/**import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.testutil.TypicalFlashCards.CHINESE;
import static seedu.address.testutil.TypicalFlashCards.ENGLISH;**/

import org.junit.Rule;
//import org.junit.Test;
import org.junit.rules.ExpectedException;

//import seedu.address.testutil.FlashCardBuilder;
public class FlashCardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    /**
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        FlashCard flashcard = new FlashCardBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        flashcard.getTags().remove(0);
    }

    @Test
    public void isSameFlashCard() {
        // same object -> returns true
        assertTrue(ENGLISH.isSameFlashCard(ENGLISH));

        // null -> returns false
        assertFalse(ENGLISH.isSameFlashCard(null));

        // different cont and email -> returns false
        FlashCard editedEnglish = new FlashCardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        assertFalse(ENGLISH.isSameFlashCard(editedEnglish));

        // different sub -> returns false
        editedEnglish = new FlashCardBuilder(ENGLISH).withSubject(VALID_SUBJECT_CHINESE).build();
        assertFalse(ENGLISH.isSameFlashCard(editedEnglish));

        // same sub, same cont, different attributes -> returns true
        editedEnglish = new FlashCardBuilder(ENGLISH).withTags(VALID_TAG_ENGLISH).build();
        assertTrue(ENGLISH.isSameFlashCard(editedEnglish));

        // same sub, same email, different attributes -> returns true
        editedEnglish = new FlashCardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH).build();
        assertTrue(ENGLISH.isSameFlashCard(editedEnglish));

        // same sub, same cont, same email, different attributes -> returns true
        editedEnglish = new FlashCardBuilder(ENGLISH).withTags(VALID_TAG_ENGLISH).build();
        assertTrue(ENGLISH.isSameFlashCard(editedEnglish));
    }

    @Test
    public void equals() {
        // same values -> returns true
        FlashCard aliceCopy = new FlashCardBuilder(ENGLISH).build();
        assertTrue(ENGLISH.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ENGLISH.equals(ENGLISH));

        // null -> returns false
        assertFalse(ENGLISH.equals(null));

        // different type -> returns false
        assertFalse(ENGLISH.equals(5));

        // different flashcard -> returns false
        assertFalse(ENGLISH.equals(CHINESE));

        // different sub -> returns false
        FlashCard editedEnglish = new FlashCardBuilder(ENGLISH).withSubject(VALID_SUBJECT_CHINESE).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different cont -> returns false
        editedEnglish = new FlashCardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different email -> returns false
        editedEnglish = new FlashCardBuilder(ENGLISH).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different address -> returns false
        editedEnglish = new FlashCardBuilder(ENGLISH).build();
        assertFalse(ENGLISH.equals(editedEnglish));

        // different tags -> returns false
        editedEnglish = new FlashCardBuilder(ENGLISH).withTags(VALID_TAG_ENGLISH).build();
        assertFalse(ENGLISH.equals(editedEnglish));
    }**/
}
