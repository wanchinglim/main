package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalFlashcards.ALICE;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.testutil.FlashcardBuilder;

public class FlashBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FlashBook flashBook = new FlashBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), flashBook.getFlashcardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        flashBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFlashBook_replacesData() {
        FlashBook newData = getTypicalFlashBook();
        flashBook.resetData(newData);
        assertEquals(newData, flashBook);
    }

    @Test
    public void resetData_withDuplicateFlashcards_throwsDuplicateFlashcardException() {
        // Two flashcards with the same identity fields
        Flashcard editedAlice = new FlashcardBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Flashcard> newFlashcards = Arrays.asList(ALICE, editedAlice);
        FlashBookStub newData = new FlashBookStub(newFlashcards);

        thrown.expect(DuplicateFlashcardException.class);
        flashBook.resetData(newData);
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        flashBook.hasFlashcard(null);
    }

    @Test
    public void hasFlashcard_flashcardNotInFlashBook_returnsFalse() {
        assertFalse(flashBook.hasFlashcard(ALICE));
    }

    @Test
    public void hasFlashcard_flashcardInFlashBook_returnsTrue() {
        flashBook.addFlashcard(ALICE);
        assertTrue(flashBook.hasFlashcard(ALICE));
    }

    @Test
    public void hasFlashcard_flashcardWithSameIdentityFieldsInFlashBook_returnsTrue() {
        flashBook.addFlashcard(ALICE);
        Flashcard editedAlice = new FlashcardBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(flashBook.hasFlashcard(editedAlice));
    }

    @Test
    public void getFlashcardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        flashBook.getFlashcardList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        flashBook.addListener(listener);
        flashBook.addFlashcard(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        flashBook.addListener(listener);
        flashBook.removeListener(listener);
        flashBook.addFlashcard(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFlashBook whose flashcards list can violate interface constraints.
     */
    private static class FlashBookStub implements ReadOnlyFlashBook {
        private final ObservableList<Flashcard> flashcards = FXCollections.observableArrayList();

        FlashBookStub(Collection<Flashcard> flashcards) {
            this.flashcards.setAll(flashcards);
        }

        @Override
        public ObservableList<Flashcard> getFlashcardList() {
            return flashcards;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
