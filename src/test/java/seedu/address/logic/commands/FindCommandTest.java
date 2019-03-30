package seedu.address.logic.commands;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalFlashcards.CARL;
//import static seedu.address.testutil.TypicalFlashcards.ELLE;
//import static seedu.address.testutil.TypicalFlashcards.FIONA;
//import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.TopicContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    //private Model model = new ModelManager(getTypicalFlashBook(), new UserPrefs());
    //private Model expectedModel = new ModelManager(getTypicalFlashBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TopicContainsKeywordsPredicate firstPredicate =
                new TopicContainsKeywordsPredicate(Collections.singletonList("first"));
        TopicContainsKeywordsPredicate secondPredicate =
                new TopicContainsKeywordsPredicate(Collections.singletonList("second"));

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

    /*@Test
    public void execute_zeroKeywords_noFlashcardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        TopicContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        TopicContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredFlashcardList());
    }*/

    /**
     * Parses {@code userInput} into a {@code TopicContainsKeywordsPredicate}.
     */
    private TopicContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TopicContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
