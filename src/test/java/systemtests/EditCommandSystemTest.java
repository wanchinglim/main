package systemtests;

/**import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;**/
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
/**import static seedu.address.testutil.TypicalFlashCards.CHINESE;
import static seedu.address.testutil.TypicalFlashCards.ENGLISH;
import static seedu.address.testutil.TypicalFlashCards.KEYWORD_MATCHING_PRINCIPLES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;**/
//import org.junit.Test;
//import seedu.address.commons.core.Messages;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
//import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
//import seedu.address.model.flashcard.Subject;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.FlashCardBuilder;
//import seedu.address.testutil.FlashCardUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {

    /**@Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

    /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
     * -> edited
     */
    /**
        Index index = INDEX_FIRST_FLASHCARD;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + SUBJECT_DESC_CHINESE
                + "  " + CONTENT_DESC_CHINESE + " " + TAG_DESC_ENGLISH + " ";
        FlashCard editedFlashCard = new FlashCardBuilder(CHINESE).withTags(VALID_TAG_ENGLISH).build();
        assertCommandSuccess(command, index, editedFlashCard);

        /* Case: undo editing the last flashcard in the list -> last flashcard restored */
    /**
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last flashcard in the list -> last flashcard edited again */
    /**
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setFlashCard(getModel().getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased()),
                editedFlashCard);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a flashcard with new values same as existing values -> edited */
    /**
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
                + TAG_DESC_CHINESE + TAG_DESC_ENGLISH;
        assertCommandSuccess(command, index, CHINESE);

        /* Case: edit a flashcard with new values same as another flashcard's values but with different sub -> edited */
    /**
        assertTrue(getModel().getAddressBook().getFlashCardList().contains(CHINESE));
        index = INDEX_SECOND_FLASHCARD;
        assertNotEquals(getModel().getFilteredFlashCardList().get(index.getZeroBased()), CHINESE);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_ENGLISH + CONTENT_DESC_CHINESE
                + TAG_DESC_CHINESE + TAG_DESC_ENGLISH;
        editedFlashCard = new FlashCardBuilder(CHINESE).withSubject(VALID_SUBJECT_ENGLISH).build();
        assertCommandSuccess(command, index, editedFlashCard);

        /* Case: edit a flashcard with new values same as another flashcard's values but with different cont and email
         * -> edited
         */
    /**
        index = INDEX_SECOND_FLASHCARD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_CHINESE + CONTENT_DESC_ENGLISH
                + TAG_DESC_CHINESE + TAG_DESC_ENGLISH;
        editedFlashCard = new FlashCardBuilder(CHINESE).withContent(VALID_CONTENT_ENGLISH).build();
        assertCommandSuccess(command, index, editedFlashCard);

        /* Case: clear tags -> cleared */
    /**
        index = INDEX_FIRST_FLASHCARD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        FlashCard flashcardToEdit = getModel().getFilteredFlashCardList().get(index.getZeroBased());
        editedFlashCard = new FlashCardBuilder(flashcardToEdit).withTags().build();
        assertCommandSuccess(command, index, editedFlashCard);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */
    /**
        /* Case: filtered flashcard list, edit index within bounds of address book and flashcard list -> edited */
    /**
        showFlashCardsWithSubject(KEYWORD_MATCHING_PRINCIPLES);
        index = INDEX_FIRST_FLASHCARD;
        assertTrue(index.getZeroBased() < getModel().getFilteredFlashCardList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + SUBJECT_DESC_CHINESE;
        flashcardToEdit = getModel().getFilteredFlashCardList().get(index.getZeroBased());
        editedFlashCard = new FlashCardBuilder(flashcardToEdit).withSubject(VALID_SUBJECT_CHINESE).build();
        assertCommandSuccess(command, index, editedFlashCard);

        /* Case: filtered flashcard list, edit index within bounds of address book but out of bounds of flashcard list
         * -> rejected
         */
    /**
        showFlashCardsWithSubject(KEYWORD_MATCHING_PRINCIPLES);
        int invalidIndex = getModel().getAddressBook().getFlashCardList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + SUBJECT_DESC_CHINESE,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        /* ------------------ Performing edit operation while a flashcard card is selected ----------------------- */

    /* Case: selects first card in the flashcard list, edit a flashcard
     * -> edited, card selection remains unchanged but browser url changes
     */
    /**
        showAllFlashCards();
        index = INDEX_FIRST_FLASHCARD;
        selectFlashCard(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_ENGLISH + CONTENT_DESC_ENGLISH
                + TAG_DESC_CHINESE;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new flashcard's sub
        assertCommandSuccess(command, index, ENGLISH, index);

        /* ------------------------------ Performing invalid edit operation ----------------------------------- */

    /* Case: invalid index (0) -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + SUBJECT_DESC_CHINESE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + SUBJECT_DESC_CHINESE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
    /**
        invalidIndex = getModel().getFilteredFlashCardList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + SUBJECT_DESC_CHINESE,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + SUBJECT_DESC_CHINESE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid sub -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased()
                + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        /* Case: invalid cont -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased()
                + INVALID_CONTENT_DESC, Content.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
    /**
        assertCommandFailure(EditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_FLASHCARD.getOneBased() + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a flashcard with new values same as another flashcard's values -> rejected */
    /**
        executeCommand(FlashCardUtil.getAddCommand(CHINESE));
        assertTrue(getModel().getAddressBook().getFlashCardList().contains(CHINESE));
        index = INDEX_FIRST_FLASHCARD;
        assertFalse(getModel().getFilteredFlashCardList().get(index.getZeroBased()).equals(CHINESE));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
                + TAG_DESC_CHINESE + TAG_DESC_ENGLISH;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: edit a flashcard with new values same as another flashcard's values but with different tags -> rej */
    /**
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
                + TAG_DESC_ENGLISH;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: edit a flashcard with new values same as another flashcard's values but w/ different cont -> rej */
    /**
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + SUBJECT_DESC_CHINESE + CONTENT_DESC_ENGLISH
                + TAG_DESC_CHINESE + TAG_DESC_ENGLISH;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, FlashCard, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, FlashCard, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, FlashCard editedFlashCard) {
        assertCommandSuccess(command, toEdit, editedFlashCard, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the flashcard at index {@code toEdit} being
     * updated to values specified {@code editedFlashCard}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, FlashCard editedFlashCard,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setFlashCard(expectedModel.getFilteredFlashCardList()
                .get(toEdit.getZeroBased()), editedFlashCard);
        expectedModel.updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
