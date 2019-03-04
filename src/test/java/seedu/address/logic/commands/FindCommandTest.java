package seedu.address.logic.commands;
/**
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.COMP_STUDIES;
import static seedu.address.testutil.TypicalFlashCards.LITERATURE;
import static seedu.address.testutil.TypicalFlashCards.PHYSICS;**/

import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.util.Arrays;
//import java.util.Collections;

//import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.ContentContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    /**
    @Test
    public void equals() {
        ContentContainsKeywordsPredicate firstPredicate =
                new ContentContainsKeywordsPredicate(Collections.singletonList("first"));
        ContentContainsKeywordsPredicate secondPredicate =
                new ContentContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        ContentContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashCardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashCardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        ContentContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PHYSICS, LITERATURE, COMP_STUDIES), model.getFilteredFlashCardList());
    }

    /**
     * Parses {@code userInput} into a {@code ContentContainsKeywordsPredicate}.
     */
    private ContentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ContentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
