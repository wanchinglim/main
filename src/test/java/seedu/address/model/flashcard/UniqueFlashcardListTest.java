package seedu.address.model.flashcard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.testutil.TypicalFlashcards.CHINESE;
import static seedu.address.testutil.TypicalFlashcards.ENGLISH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;
import seedu.address.testutil.FlashcardBuilder;

public class UniqueFlashcardListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueFlashcardList uniqueFlashcardList = new UniqueFlashcardList();

    @Test
    public void contains_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.contains(null);
    }

    @Test
    public void contains_flashcardNotInList_returnsFalse() {
        assertFalse(uniqueFlashcardList.contains(ENGLISH));
    }

    @Test
    public void contains_flashcardInList_returnsTrue() {
        uniqueFlashcardList.add(ENGLISH);
        assertTrue(uniqueFlashcardList.contains(ENGLISH));
    }

    @Test
    public void contains_flashcardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashcardList.add(ENGLISH);
        Flashcard editedAlice = new FlashcardBuilder(ENGLISH)
                .withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH)
                .build();
        assertTrue(uniqueFlashcardList.contains(editedAlice));
    }

    @Test
    public void add_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.add(null);
    }

    @Test
    public void add_duplicateFlashcard_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(ENGLISH);
        thrown.expect(DuplicateFlashcardException.class);
        uniqueFlashcardList.add(ENGLISH);
    }

    @Test
    public void setFlashcard_nullTargetFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcard(null, ENGLISH);
    }

    @Test
    public void setFlashcard_nullEditedFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcard(ENGLISH, null);
    }

    @Test
    public void setFlashcard_targetFlashcardNotInList_throwsFlashcardNotFoundException() {
        thrown.expect(FlashcardNotFoundException.class);
        uniqueFlashcardList.setFlashcard(ENGLISH, ENGLISH);
    }

    @Test
    public void setFlashcard_editedFlashcardIsSameFlashcard_success() {
        uniqueFlashcardList.add(ENGLISH);
        uniqueFlashcardList.setFlashcard(ENGLISH, ENGLISH);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(ENGLISH);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasSameIdentity_success() {
        uniqueFlashcardList.add(ENGLISH);
        Flashcard editedAlice = new FlashcardBuilder(ENGLISH)
                .withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH)
                .build();
        uniqueFlashcardList.setFlashcard(ENGLISH, editedAlice);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(editedAlice);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    /*@Test
    public void setFlashcard_editedFlashcardHasDifferentIdentity_success() {
        uniqueFlashcardList.add(ENGLISH);
        uniqueFlashcardList.setFlashcard(ENGLISH, CHINESE);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(ENGLISH);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }*/

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueIdentity_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(ENGLISH);
        uniqueFlashcardList.add(CHINESE);
        thrown.expect(DuplicateFlashcardException.class);
        uniqueFlashcardList.setFlashcard(ENGLISH, CHINESE);
    }

    @Test
    public void remove_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.remove(null);
    }

    @Test
    public void remove_flashcardDoesNotExist_throwsFlashcardNotFoundException() {
        thrown.expect(FlashcardNotFoundException.class);
        uniqueFlashcardList.remove(ENGLISH);
    }

    @Test
    public void remove_existingFlashcard_removesFlashcard() {
        uniqueFlashcardList.add(ENGLISH);
        uniqueFlashcardList.remove(ENGLISH);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullUniqueFlashcardList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcards((UniqueFlashcardList) null);
    }

    @Test
    public void setFlashcards_uniqueFlashcardList_replacesOwnListWithProvidedUniqueFlashcardList() {
        uniqueFlashcardList.add(ENGLISH);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(CHINESE);
        uniqueFlashcardList.setFlashcards(expectedUniqueFlashcardList);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcards((List<Flashcard>) null);
    }

    @Test
    public void setFlashcards_list_replacesOwnListWithProvidedList() {
        uniqueFlashcardList.add(ENGLISH);
        List<Flashcard> flashcardList = Collections.singletonList(CHINESE);
        uniqueFlashcardList.setFlashcards(flashcardList);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(CHINESE);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_listWithDuplicateFlashcards_throwsDuplicateFlashcardException() {
        List<Flashcard> listWithDuplicateFlashcards = Arrays.asList(ENGLISH, ENGLISH);
        thrown.expect(DuplicateFlashcardException.class);
        uniqueFlashcardList.setFlashcards(listWithDuplicateFlashcards);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueFlashcardList.asUnmodifiableObservableList().remove(0);
    }
}
