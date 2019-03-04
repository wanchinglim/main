package seedu.address.logic.commands;

/**import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashCardAtIndex;**/
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

//import org.junit.Test;

//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
//import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
//import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
/**import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;
import seedu.address.testutil.FlashCardBuilder;**/

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    /**
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        FlashCard editedFlashCard = new FlashCardBuilder().build();
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(editedFlashCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(model.getFilteredFlashCardList().get(0), editedFlashCard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFlashCard = Index.fromOneBased(model.getFilteredFlashCardList().size());
        FlashCard lastFlashCard = model.getFilteredFlashCardList().get(indexLastFlashCard.getZeroBased());

        FlashCardBuilder flashcardInList = new FlashCardBuilder(lastFlashCard);
        FlashCard editedFlashCard = flashcardInList.withSubject(VALID_SUBJECT_CHINESE)
                .withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH).build();

        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder().withSubject(VALID_SUBJECT_CHINESE)
                .withContent(VALID_CONTENT_CHINESE).withTags(VALID_TAG_ENGLISH).build();
        EditCommand editCommand = new EditCommand(indexLastFlashCard, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(lastFlashCard, editedFlashCard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, new EditFlashCardDescriptor());
        FlashCard editedFlashCard = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFlashCardAtIndex(model, INDEX_FIRST_FLASHCARD);

        FlashCard flashcardInFilteredList = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        FlashCard editedFlashCard = new FlashCardBuilder(flashcardInFilteredList)
                .withSubject(VALID_SUBJECT_CHINESE).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashCardDescriptorBuilder().withSubject(VALID_SUBJECT_CHINESE).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(model.getFilteredFlashCardList().get(0), editedFlashCard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFlashCardUnfilteredList_failure() {
        FlashCard firstFlashCard = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(firstFlashCard).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_FLASHCARD, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_duplicateFlashCardFilteredList_failure() {
        showFlashCardAtIndex(model, INDEX_FIRST_FLASHCARD);

        // edit flashcard in filtered list into a duplicate in address book
        FlashCard flashcardInList = model.getAddressBook().getFlashCardList()
                .get(INDEX_SECOND_FLASHCARD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashCardDescriptorBuilder(flashcardInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_invalidFlashCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashCardList().size() + 1);
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder()
                .withSubject(VALID_SUBJECT_CHINESE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    /**
    @Test
    public void execute_invalidFlashCardIndexFilteredList_failure() {
        showFlashCardAtIndex(model, INDEX_FIRST_FLASHCARD);
        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFlashCardList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFlashCardDescriptorBuilder().withSubject(VALID_SUBJECT_CHINESE).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        FlashCard editedFlashCard = new FlashCardBuilder().build();
        FlashCard flashcardToEdit = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(editedFlashCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(flashcardToEdit, editedFlashCard);
        expectedModel.commitAddressBook();

        // edit -> first flashcard edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered flashcard list to show all flashcards
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first flashcard edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashCardList().size() + 1);
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder()
                .withSubject(VALID_SUBJECT_CHINESE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code FlashCard} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited flashcard in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the flashcard object regardless of indexing.
     */
    /**
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameFlashCardEdited() throws Exception {
        FlashCard editedFlashCard = new FlashCardBuilder().build();
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(editedFlashCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showFlashCardAtIndex(model, INDEX_SECOND_FLASHCARD);
        FlashCard flashcardToEdit = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        expectedModel.setFlashCard(flashcardToEdit, editedFlashCard);
        expectedModel.commitAddressBook();

        // edit -> edits second flashcard in unfiltered flashcard list / first flashcard in filtered flashcard list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered flashcard list to show all flashcards
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased()), flashcardToEdit);
        // redo -> edits same second flashcard in unfiltered flashcard list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_FLASHCARD, DESC_ENGLISH);

        // same values -> returns true
        EditFlashCardDescriptor copyDescriptor = new EditFlashCardDescriptor(DESC_ENGLISH);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_FLASHCARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_FLASHCARD, DESC_ENGLISH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_FLASHCARD, DESC_CHINESE)));
    }**/

}
