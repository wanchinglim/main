package seedu.address.model.flashcard.exceptions;

/**
 * Signals that the operation will result in duplicate FlashCards
 * (FlashCards are considered duplicates if they have the same identity).
 */
public class DuplicateFlashCardException extends RuntimeException {
    public DuplicateFlashCardException() {
        super("Operation would result in duplicate flashcards");
    }
}
