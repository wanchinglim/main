package systemtests;

//import static org.junit.Assert.assertFalse;
//import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
/**import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalFlashCards.CHEMISTRY;
import static seedu.address.testutil.TypicalFlashCards.COMP_STUDIES;
import static seedu.address.testutil.TypicalFlashCards.KEYWORD_MATCHING_PRINCIPLES;
import static seedu.address.testutil.TypicalFlashCards.PHYSICS;

import java.util.ArrayList;
import java.util.List;**/

//import org.junit.Test;

/**import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;**/

//import seedu.address.model.Model;
//import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    /**@Test
    public void find() {
        /* Case: find multiple flashcards in address book, command with leading spaces and trailing spaces
         * -> 2 flashcards found
         */
    /**
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_PRINCIPLES + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CHEMISTRY, COMP_STUDIES);
        // first subs of Chemistry and Computer Studies are "Principles"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where flashcard list is displaying the flashcards we are finding
         * -> 2 flashcards found
         */
    /**
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_PRINCIPLES;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find flashcard where flashcard list is not displaying the flashcard we are finding
        -> 1 flashcard found */
    /**
        command = FindCommand.COMMAND_WORD + " Physics";
        ModelHelper.setFilteredList(expectedModel, PHYSICS);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple flashcards in address book, 2 keywords -> 2 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " Chemistry Computer Studies";
        ModelHelper.setFilteredList(expectedModel, CHEMISTRY, COMP_STUDIES);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple flashcards in address book, 2 keywords in reversed order -> 2 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " Computer Studies Chemistry";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple flashcards in address book, 2 keywords with 1 repeat -> 2 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " Computer Studies Chemistry Computer Studies";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple flashcards in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 flashcards found
         */
    /**
        command = FindCommand.COMMAND_WORD + " Computer Studies Chemistry NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
    /**
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
    /**
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same flashcards in address book after deleting 1 of them -> 1 flashcard found */
    /**
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getFlashCardList().contains(CHEMISTRY));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_PRINCIPLES;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, COMP_STUDIES);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find flashcard in address book, keyword is same as sub but of different case -> 1 flashcard found */
    /**
        command = FindCommand.COMMAND_WORD + " PrInCiPlEs";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find flashcard in address book, keyword is substring of sub -> 0 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " Pri";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find flashcard in address book, sub is substring of keyword -> 0 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " Principles";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find flashcard not in address book -> 0 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find cont number of flashcard in address book -> 0 flashcards found */
    /**
        command = FindCommand.COMMAND_WORD + " " + COMP_STUDIES.getContent().content;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of flashcard in address book -> 0 flashcards found */
    /**
        List<Tag> tags = new ArrayList<>(COMP_STUDIES.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a flashcard is selected -> selected card deselected */
    /**
        showAllFlashCards();
        selectFlashCard(Index.fromOneBased(1));
        assertFalse(getFlashCardListPanel().getHandleToSelectedCard().getSubject()
                .equals(COMP_STUDIES.getSubject().subject));
        command = FindCommand.COMMAND_WORD + " Computer Studies";
        ModelHelper.setFilteredList(expectedModel, COMP_STUDIES);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find flashcard in empty address book -> 0 flashcards found */
    /**
        deleteAllFlashCards();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_PRINCIPLES;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, COMP_STUDIES);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
    /**
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_FLASHCARDS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    /**
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_FLASHCARDS_LISTED_OVERVIEW, expectedModel.getFilteredFlashCardList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }**/

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    /**
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }**/
}
