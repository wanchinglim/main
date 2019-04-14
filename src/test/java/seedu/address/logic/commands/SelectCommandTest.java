package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalSubjects.getTypicalSubjectBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.SubjectTag;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {

    private Model model = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        model.setSelectedSubject(new SubjectTag("english"));
        Index outOfBoundsIndex = Index.fromOneBased(model.getUpdatedFlashcardList().size() + 2);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model.setSelectedSubject(new SubjectTag("english"));
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        expectedModel.setSelectedSubject(new SubjectTag("english"));
        showFlashcardAtIndex(expectedModel, INDEX_FIRST_FLASHCARD);

        assertExecutionSuccess(INDEX_FIRST_FLASHCARD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        model.setSelectedSubject(new SubjectTag("english"));
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        expectedModel.setSelectedSubject(new SubjectTag("english"));
        showFlashcardAtIndex(expectedModel, INDEX_FIRST_FLASHCARD);

        Index outOfBoundsIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of flash book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getFlashBook().getFlashcardList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_FLASHCARD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_FLASHCARD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_FLASHCARD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected flashcard is set to the flashcard at
     * {@code index} in the filtered flashcard list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_FLASHCARD_SUCCESS, index.getOneBased());
        expectedModel.setSelectedFlashcard(model.getFilteredFlashcardList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
