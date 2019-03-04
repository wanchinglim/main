package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.testutil.TypicalFlashCards.MALAY;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

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
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
import seedu.address.testutil.FlashCardBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getFlashCardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateFlashCards_throwsDuplicateFlashCardException() {
        // Two flashcards with the same identity fields
        FlashCard editedMalay = new FlashCardBuilder(MALAY).withTags(VALID_TAG_ENGLISH).build();
        List<FlashCard> newFlashCards = Arrays.asList(MALAY, editedMalay);
        AddressBookStub newData = new AddressBookStub(newFlashCards);

        thrown.expect(DuplicateFlashCardException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasFlashCard_nullFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasFlashCard(null);
    }

    @Test
    public void hasFlashCard_flashcardNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasFlashCard(MALAY));
    }

    @Test
    public void hasFlashCard_flashcardInAddressBook_returnsTrue() {
        addressBook.addFlashCard(MALAY);
        assertTrue(addressBook.hasFlashCard(MALAY));
    }

    @Test
    public void hasFlashCard_flashcardWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addFlashCard(MALAY);
        FlashCard editedMalay = new FlashCardBuilder(MALAY).withTags(VALID_TAG_ENGLISH)
                .build();
        assertTrue(addressBook.hasFlashCard(editedMalay));
    }

    @Test
    public void getFlashCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getFlashCardList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.addFlashCard(MALAY);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        addressBook.addListener(listener);
        addressBook.removeListener(listener);
        addressBook.addFlashCard(MALAY);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyAddressBook whose flashcards list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<FlashCard> flashcards = FXCollections.observableArrayList();

        AddressBookStub(Collection<FlashCard> flashcards) {
            this.flashcards.setAll(flashcards);
        }

        @Override
        public ObservableList<FlashCard> getFlashCardList() {
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
