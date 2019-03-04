package seedu.address.model.flashcard;

/**import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.testutil.TypicalFlashCards.CHINESE;
import static seedu.address.testutil.TypicalFlashCards.ENGLISH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;**/

import org.junit.Rule;
//import org.junit.Test;
import org.junit.rules.ExpectedException;

//import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
//import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;
//import seedu.address.testutil.FlashCardBuilder;

public class UniqueFlashCardListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueFlashCardList uniqueFlashCardList = new UniqueFlashCardList();
    /**
    @Test
    public void contains_nullFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.contains(null);
    }

    @Test
    public void contains_flashcardNotInList_returnsFalse() {
        assertFalse(uniqueFlashCardList.contains(ENGLISH));
    }

    @Test
    public void contains_flashcardInList_returnsTrue() {
        uniqueFlashCardList.add(ENGLISH);
        assertTrue(uniqueFlashCardList.contains(ENGLISH));
    }

    @Test
    public void contains_flashcardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashCardList.add(ENGLISH);
        FlashCard editedAlice = new FlashCardBuilder(ENGLISH).withTags(VALID_TAG_ENGLISH)
                .build();
        assertTrue(uniqueFlashCardList.contains(editedAlice));
    }

    @Test
    public void add_nullFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.add(null);
    }

    @Test
    public void add_duplicateFlashCard_throwsDuplicateFlashCardException() {
        uniqueFlashCardList.add(ENGLISH);
        thrown.expect(DuplicateFlashCardException.class);
        uniqueFlashCardList.add(ENGLISH);
    }

    @Test
    public void setFlashCard_nullTargetFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.setFlashCard(null, ENGLISH);
    }

    @Test
    public void setFlashCard_nullEditedFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.setFlashCard(ENGLISH, null);
    }

    @Test
    public void setFlashCard_targetFlashCardNotInList_throwsFlashCardNotFoundException() {
        thrown.expect(FlashCardNotFoundException.class);
        uniqueFlashCardList.setFlashCard(ENGLISH, ENGLISH);
    }

    @Test
    public void setFlashCard_editedFlashCardIsSameFlashCard_success() {
        uniqueFlashCardList.add(ENGLISH);
        uniqueFlashCardList.setFlashCard(ENGLISH, ENGLISH);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(ENGLISH);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCard_editedFlashCardHasSameIdentity_success() {
        uniqueFlashCardList.add(ENGLISH);
        FlashCard editedAlice = new FlashCardBuilder(ENGLISH).withTags(VALID_TAG_ENGLISH)
                .build();
        uniqueFlashCardList.setFlashCard(ENGLISH, editedAlice);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(editedAlice);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCard_editedFlashCardHasDifferentIdentity_success() {
        uniqueFlashCardList.add(ENGLISH);
        uniqueFlashCardList.setFlashCard(ENGLISH, CHINESE);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(CHINESE);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCard_editedFlashCardHasNonUniqueIdentity_throwsDuplicateFlashCardException() {
        uniqueFlashCardList.add(ENGLISH);
        uniqueFlashCardList.add(CHINESE);
        thrown.expect(DuplicateFlashCardException.class);
        uniqueFlashCardList.setFlashCard(ENGLISH, CHINESE);
    }

    @Test
    public void remove_nullFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.remove(null);
    }

    @Test
    public void remove_flashcardDoesNotExist_throwsFlashCardNotFoundException() {
        thrown.expect(FlashCardNotFoundException.class);
        uniqueFlashCardList.remove(ENGLISH);
    }

    @Test
    public void remove_existingFlashCard_removesFlashCard() {
        uniqueFlashCardList.add(ENGLISH);
        uniqueFlashCardList.remove(ENGLISH);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCards_nullUniqueFlashCardList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.setFlashCards((UniqueFlashCardList) null);
    }

    @Test
    public void setFlashCards_uniqueFlashCardList_replacesOwnListWithProvidedUniqueFlashCardList() {
        uniqueFlashCardList.add(ENGLISH);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(CHINESE);
        uniqueFlashCardList.setFlashCards(expectedUniqueFlashCardList);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCards_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashCardList.setFlashCards((List<FlashCard>) null);
    }

    @Test
    public void setFlashCards_list_replacesOwnListWithProvidedList() {
        uniqueFlashCardList.add(ENGLISH);
        List<FlashCard> flashcardList = Collections.singletonList(CHINESE);
        uniqueFlashCardList.setFlashCards(flashcardList);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(CHINESE);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCards_listWithDuplicateFlashCards_throwsDuplicateFlashCardException() {
        List<FlashCard> listWithDuplicateFlashCards = Arrays.asList(ENGLISH, ENGLISH);
        thrown.expect(DuplicateFlashCardException.class);
        uniqueFlashCardList.setFlashCards(listWithDuplicateFlashCards);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueFlashCardList.asUnmodifiableObservableList().remove(0);
    }**/

}
