package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code FlashBook} that keeps track of its own history.
 */
public class VersionedFlashBook extends FlashBook {

    private final List<ReadOnlyFlashBook> flashBookStateList;
    private int currentStatePointer;

    public VersionedFlashBook(ReadOnlyFlashBook initialState) {
        super(initialState);

        flashBookStateList = new ArrayList<>();
        flashBookStateList.add(new FlashBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code FlashBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        flashBookStateList.add(new FlashBook(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        flashBookStateList.subList(currentStatePointer + 1, flashBookStateList.size()).clear();
    }

    /**
     * Restores the flash book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(flashBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the flash book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(flashBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has flash book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has flash book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < flashBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedFlashBook)) {
            return false;
        }

        VersionedFlashBook otherVersionedFlashBook = (VersionedFlashBook) other;

        // state check
        return super.equals(otherVersionedFlashBook)
                && flashBookStateList.equals(otherVersionedFlashBook.flashBookStateList)
                && currentStatePointer == otherVersionedFlashBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of flashBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of flashBookState list, unable to redo.");
        }
    }
}
