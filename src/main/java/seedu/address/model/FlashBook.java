package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.UniqueFlashcardList;

/**
 * Wraps all data at the flash-book level
 * Duplicates are not allowed (by .isSameFlashcard comparison)
 */
public class FlashBook implements ReadOnlyFlashBook {

    private final UniqueFlashcardList flashcards;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        flashcards = new UniqueFlashcardList();
    }

    public FlashBook() {}

    /**
     * Creates an FlashBook using the Flashcards in the {@code toBeCopied}
     */
    public FlashBook(ReadOnlyFlashBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the flashcard list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards.setFlashcards(flashcards);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code FlashBook} with {@code newData}.
     */
    public void resetData(ReadOnlyFlashBook newData) {
        requireNonNull(newData);

        setFlashcards(newData.getFlashcardList());
    }

    //// flashcard-level operations

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the flash book.
     */
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
    }

    /**
     * Adds a flashcard to the flash book.
     * The flashcard must not already exist in the flash book.
     */
    public void addFlashcard(Flashcard p) {
        flashcards.add(p);
        indicateModified();
    }

    /**
     * Replaces the given flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the flash book.
     * The flashcard identity of {@code editedFlashcard} must not be the same as another
     * existing flashcard in the flash book.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);

        flashcards.setFlashcard(target, editedFlashcard);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FlashBook}.
     * {@code key} must exist in the flash book.
     */
    public void removeFlashcard(Flashcard key) {
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
     * Notifies listeners that the flash book has been modified.
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
    public ObservableList<Flashcard> getFlashcardList() {
        return flashcards.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashBook // instanceof handles nulls
                && flashcards.equals(((FlashBook) other).flashcards));
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }
}
