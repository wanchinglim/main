package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.UniqueFlashCardList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFlashCard comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueFlashCardList flashcards;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        flashcards = new UniqueFlashCardList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the FlashCards in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the flashcard list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashCards(List<FlashCard> flashcards) {
        this.flashcards.setFlashCards(flashcards);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setFlashCards(newData.getFlashCardList());
    }

    //// flashcard-level operations

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the address book.
     */
    public boolean hasFlashCard(FlashCard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
    }

    /**
     * Adds a flashcard to the address book.
     * The flashcard must not already exist in the address book.
     */
    public void addFlashCard(FlashCard p) {
        flashcards.add(p);
        indicateModified();
    }

    /**
     * Replaces the given flashcard {@code target} in the list with {@code editedFlashCard}.
     * {@code target} must exist in the address book.
     * The flashcard identity of {@code editedFlashCard} must not be the same as another
     * existing flashcard in the address book.
     */
    public void setFlashCard(FlashCard target, FlashCard editedFlashCard) {
        requireNonNull(editedFlashCard);

        flashcards.setFlashCard(target, editedFlashCard);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeFlashCard(FlashCard key) {
        flashcards.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return flashcards.asUnmodifiableObservableList().size() + " flashcards";
        // TODO: refine later
    }

    @Override
    public ObservableList<FlashCard> getFlashCardList() {
        return flashcards.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && flashcards.equals(((AddressBook) other).flashcards));
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }
}
