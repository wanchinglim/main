package systemtests;

/**import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalFlashCards.ACCOUNTING;
import static seedu.address.testutil.TypicalFlashCards.CHINESE;
import static seedu.address.testutil.TypicalFlashCards.ENGLISH;
import static seedu.address.testutil.TypicalFlashCards.KEYWORD_MATCHING_PRINCIPLES;
import static seedu.address.testutil.TypicalFlashCards.MALAY;
import static seedu.address.testutil.TypicalFlashCards.PHYSICS;
import static seedu.address.testutil.TypicalFlashCards.PSYCH;

//import org.junit.Test;**/

//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
//import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
//import seedu.address.model.flashcard.Subject;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.FlashCardBuilder;
import seedu.address.testutil.FlashCardUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    /**@Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

    /* Case: add a flashcard without tags to a non-empty address book,
     * command with leading spaces and trailing spaces -> added
     */
    /**
        FlashCard toAdd = ENGLISH;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + SUBJECT_DESC_ENGLISH + "  "
                + CONTENT_DESC_ENGLISH + "   " + TAG_DESC_CHINESE + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding English to the list -> English deleted */
    /**
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding English to the list -> English added again */
    /**
        command = RedoCommand.COMMAND_WORD;
        model.addFlashCard(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a flashcard with all fields same as another flashcard in the address book except subject-> added */
    /**
        toAdd = new FlashCardBuilder(ENGLISH).withSubject(VALID_SUBJECT_CHINESE).build();
        command = AddCommand.COMMAND_WORD + SUBJECT_DESC_CHINESE + CONTENT_DESC_ENGLISH
                + TAG_DESC_CHINESE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a flashcard with all fields same as another flashcard in the address book except content and email
         * -> added
         */
    /**
        toAdd = new FlashCardBuilder(ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        command = FlashCardUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
    /**
        deleteAllFlashCards();
        assertCommandSuccess(MALAY);

        /* Case: add a flashcard with tags, command with parameters in random order -> added */
    /**
        toAdd = CHINESE;
        command = AddCommand.COMMAND_WORD + TAG_DESC_CHINESE + CONTENT_DESC_CHINESE + SUBJECT_DESC_CHINESE
                + TAG_DESC_ENGLISH;
        assertCommandSuccess(command, toAdd);

        /* Case: add a flashcard, missing tags -> added */
    /**
        assertCommandSuccess(PSYCH);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

    /* Case: filters the flashcard list before adding -> added */
    /**
        showFlashCardsWithSubject(KEYWORD_MATCHING_PRINCIPLES);
        assertCommandSuccess(ACCOUNTING);

        /* ------------------- Perform add operation while a flashcard card is selected ---------------------- */

    /* Case: selects first card in the flashcard list, add a flashcard -> added, card selection remains unchanged */
    /**
        selectFlashCard(Index.fromOneBased(1));
        assertCommandSuccess(PHYSICS);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

    /* Case: add a duplicate flashcard -> rejected */
    /**
        command = FlashCardUtil.getAddCommand(PSYCH);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: add a duplicate flashcard except with different content -> rejected */
    /**
        toAdd = new FlashCardBuilder(PSYCH).withContent(VALID_CONTENT_CHINESE).build();
        command = FlashCardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: add a duplicate flashcard except with different tags -> rejected */
    /**
        command = FlashCardUtil.getAddCommand(PSYCH) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: missing subject -> rejected */
    /**
        command = AddCommand.COMMAND_WORD + CONTENT_DESC_ENGLISH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing content -> rejected */
    /**
        command = AddCommand.COMMAND_WORD + SUBJECT_DESC_ENGLISH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
    /**
        command = "adds " + FlashCardUtil.getFlashCardDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid subject -> rejected */
    /**
        command = AddCommand.COMMAND_WORD + INVALID_SUBJECT_DESC + CONTENT_DESC_ENGLISH;
        assertCommandFailure(command, Subject.MESSAGE_CONSTRAINTS);

        /* Case: invalid content -> rejected */
    /**
        command = AddCommand.COMMAND_WORD + SUBJECT_DESC_ENGLISH + INVALID_CONTENT_DESC;
        assertCommandFailure(command, Content.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
    /**
        command = AddCommand.COMMAND_WORD + SUBJECT_DESC_ENGLISH + CONTENT_DESC_ENGLISH + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code FlashCardListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(FlashCard toAdd) {
        assertCommandSuccess(FlashCardUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(FlashCard)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(FlashCard)
     */
    private void assertCommandSuccess(String command, FlashCard toAdd) {
        Model expectedModel = getModel();
        expectedModel.addFlashCard(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, FlashCard)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code FlashCardListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, FlashCard)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code FlashCardListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
