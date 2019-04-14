package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalFlashcards.CHINESE;
import static seedu.address.testutil.TypicalFlashcards.ENGLISH;
import static seedu.address.testutil.TypicalFlashcards.MATH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.FlashBookBuilder;

public class VersionedFlashBookTest {

    private final ReadOnlyFlashBook flashBookWithAmy = new FlashBookBuilder().withFlashcard(ENGLISH).build();
    private final ReadOnlyFlashBook flashBookWithBob = new FlashBookBuilder().withFlashcard(CHINESE).build();
    private final ReadOnlyFlashBook flashBookWithCarl = new FlashBookBuilder().withFlashcard(MATH).build();
    private final ReadOnlyFlashBook emptyFlashBook = new FlashBookBuilder().build();

    @Test
    public void commit_singleFlashBook_noStatesRemovedCurrentStateSaved() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(emptyFlashBook);

        versionedFlashBook.commit();
        assertFlashBookListStatus(versionedFlashBook,
                Collections.singletonList(emptyFlashBook),
                emptyFlashBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFlashBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);

        versionedFlashBook.commit();
        assertFlashBookListStatus(versionedFlashBook,
                Arrays.asList(emptyFlashBook, flashBookWithAmy, flashBookWithBob),
                flashBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFlashBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 2);

        versionedFlashBook.commit();
        assertFlashBookListStatus(versionedFlashBook,
                Collections.singletonList(emptyFlashBook),
                emptyFlashBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleFlashBookPointerAtEndOfStateList_returnsTrue() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);

        assertTrue(versionedFlashBook.canUndo());
    }

    @Test
    public void canUndo_multipleFlashBookPointerAtStartOfStateList_returnsTrue() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 1);

        assertTrue(versionedFlashBook.canUndo());
    }

    @Test
    public void canUndo_singleFlashBook_returnsFalse() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(emptyFlashBook);

        assertFalse(versionedFlashBook.canUndo());
    }

    @Test
    public void canUndo_multipleFlashBookPointerAtStartOfStateList_returnsFalse() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 2);

        assertFalse(versionedFlashBook.canUndo());
    }

    @Test
    public void canRedo_multipleFlashBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 1);

        assertTrue(versionedFlashBook.canRedo());
    }

    @Test
    public void canRedo_multipleFlashBookPointerAtStartOfStateList_returnsTrue() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 2);

        assertTrue(versionedFlashBook.canRedo());
    }

    @Test
    public void canRedo_singleFlashBook_returnsFalse() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(emptyFlashBook);

        assertFalse(versionedFlashBook.canRedo());
    }

    @Test
    public void canRedo_multipleFlashBookPointerAtEndOfStateList_returnsFalse() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);

        assertFalse(versionedFlashBook.canRedo());
    }

    @Test
    public void undo_multipleFlashBookPointerAtEndOfStateList_success() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);

        versionedFlashBook.undo();
        assertFlashBookListStatus(versionedFlashBook,
                Collections.singletonList(emptyFlashBook),
                flashBookWithAmy,
                Collections.singletonList(flashBookWithBob));
    }

    @Test
    public void undo_multipleFlashBookPointerNotAtStartOfStateList_success() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 1);

        versionedFlashBook.undo();
        assertFlashBookListStatus(versionedFlashBook,
                Collections.emptyList(),
                emptyFlashBook,
                Arrays.asList(flashBookWithAmy, flashBookWithBob));
    }

    @Test
    public void undo_singleFlashBook_throwsNoUndoableStateException() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(emptyFlashBook);

        assertThrows(VersionedFlashBook.NoUndoableStateException.class, versionedFlashBook::undo);
    }

    @Test
    public void undo_multipleFlashBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 2);

        assertThrows(VersionedFlashBook.NoUndoableStateException.class, versionedFlashBook::undo);
    }

    @Test
    public void redo_multipleFlashBookPointerNotAtEndOfStateList_success() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 1);

        versionedFlashBook.redo();
        assertFlashBookListStatus(versionedFlashBook,
                Arrays.asList(emptyFlashBook, flashBookWithAmy),
                flashBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleFlashBookPointerAtStartOfStateList_success() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 2);

        versionedFlashBook.redo();
        assertFlashBookListStatus(versionedFlashBook,
                Collections.singletonList(emptyFlashBook),
                flashBookWithAmy,
                Collections.singletonList(flashBookWithBob));
    }

    @Test
    public void redo_singleFlashBook_throwsNoRedoableStateException() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(emptyFlashBook);

        assertThrows(VersionedFlashBook.NoRedoableStateException.class, versionedFlashBook::redo);
    }

    @Test
    public void redo_multipleFlashBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(
                emptyFlashBook, flashBookWithAmy, flashBookWithBob);

        assertThrows(VersionedFlashBook.NoRedoableStateException.class, versionedFlashBook::redo);
    }

    @Test
    public void equals() {
        VersionedFlashBook versionedFlashBook = prepareFlashBookList(flashBookWithAmy, flashBookWithBob);

        // same values -> returns true
        VersionedFlashBook copy = prepareFlashBookList(flashBookWithAmy, flashBookWithBob);
        assertTrue(versionedFlashBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedFlashBook.equals(versionedFlashBook));

        // null -> returns false
        assertFalse(versionedFlashBook.equals(null));

        // different types -> returns false
        assertFalse(versionedFlashBook.equals(1));

        // different state list -> returns false
        VersionedFlashBook differentFlashBookList = prepareFlashBookList(flashBookWithBob, flashBookWithCarl);
        assertFalse(versionedFlashBook.equals(differentFlashBookList));

        // different current pointer index -> returns false
        VersionedFlashBook differentCurrentStatePointer = prepareFlashBookList(
                flashBookWithAmy, flashBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFlashBook, 1);
        assertFalse(versionedFlashBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedFlashBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedFlashBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedFlashBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertFlashBookListStatus(VersionedFlashBook versionedFlashBook,
                                           List<ReadOnlyFlashBook> expectedStatesBeforePointer,
                                           ReadOnlyFlashBook expectedCurrentState,
                                           List<ReadOnlyFlashBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new FlashBook(versionedFlashBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedFlashBook.canUndo()) {
            versionedFlashBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyFlashBook expectedFlashBook : expectedStatesBeforePointer) {
            assertEquals(expectedFlashBook, new FlashBook(versionedFlashBook));
            versionedFlashBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyFlashBook expectedFlashBook : expectedStatesAfterPointer) {
            versionedFlashBook.redo();
            assertEquals(expectedFlashBook, new FlashBook(versionedFlashBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedFlashBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedFlashBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedFlashBook} with the {@code flashBookStates} added into it, and the
     * {@code VersionedFlashBook#currentStatePointer} at the end of list.
     */
    private VersionedFlashBook prepareFlashBookList(ReadOnlyFlashBook... flashBookStates) {
        assertFalse(flashBookStates.length == 0);

        VersionedFlashBook versionedFlashBook = new VersionedFlashBook(flashBookStates[0]);
        for (int i = 1; i < flashBookStates.length; i++) {
            versionedFlashBook.resetData(flashBookStates[i]);
            versionedFlashBook.commit();
        }

        return versionedFlashBook;
    }

    /**
     * Shifts the {@code versionedFlashBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedFlashBook versionedFlashBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedFlashBook.undo();
        }
    }
}
