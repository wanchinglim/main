package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalSubjects.getTypicalSubjectBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.TopicContainsKeywordsPredicate;
import seedu.address.model.tag.SubjectTag;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FLASHCARD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FLASHCARD_SUCCESS, flashcardToDelete);

        model.setSelectedSubject(new SubjectTag("english"));

        ModelManager expectedModel = new ModelManager(model.getSubjectBook(), model.getFlashBook(), new UserPrefs());
        expectedModel.deleteFlashcard(flashcardToDelete);
        expectedModel.commitFlashBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
        model.setSelectedSubject(new SubjectTag("english"));
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("english"));

        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FLASHCARD);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FLASHCARD_SUCCESS, flashcardToDelete);

        Model expectedModel = new ModelManager(model.getSubjectBook(), model.getFlashBook(), new UserPrefs());
        final String[] splitName = flashcardToDelete.getTopic().fullTopic.split("\\s+");

        expectedModel.updateFilteredFlashcardList(new TopicContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        expectedModel.deleteSubject(flashcardToDelete.getSubject());
        expectedModel.deleteFlashcard(flashcardToDelete);
        expectedModel.commitFlashBook();
        expectedModel.updateFilteredFlashcardList(Model.PREDICATE_SHOW_ALL_FLASHCARDS);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("chinese"));

        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of flash book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFlashBook().getFlashcardList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        model.setSelectedSubject(new SubjectTag("english"));
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FLASHCARD);
        Model expectedModel = new ModelManager(model.getSubjectBook(), model.getFlashBook(), new UserPrefs());
        expectedModel.deleteFlashcard(flashcardToDelete);
        expectedModel.commitFlashBook();

        // delete -> first flashcard deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered flashcard list to show all subjects
        expectedModel.undoFlashBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first flashcard deleted again
        expectedModel.redoFlashBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
        model.setSelectedSubject(new SubjectTag("english"));

        // execution failed -> flash book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        // single flash book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Flashcard} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted flashcard in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the flashcard object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameFlashcardDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("english"));
        Model expectedModel = new ModelManager(model.getSubjectBook(), model.getFlashBook(), new UserPrefs());

        showFlashcardAtIndex(model, INDEX_SECOND_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("chinese"));
        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        expectedModel.deleteFlashcard(flashcardToDelete);
        expectedModel.commitFlashBook();

        // delete -> deletes second flashcard in unfiltered flashcard list / first flashcard in filtered flashcard list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts flashbook back to previous state and filtered flashcard list to show all subjects
        expectedModel.undoFlashBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(flashcardToDelete, model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased()));
        // redo -> deletes same second flashcard in unfiltered flashcard list
        expectedModel.redoFlashBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("english"));
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("chinese"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_FLASHCARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */

    private void showNoFlashcard(Model model) {
        model.updateFilteredFlashcardList(p -> false);

        assertTrue(model.getFilteredFlashcardList().isEmpty());
    }

}
