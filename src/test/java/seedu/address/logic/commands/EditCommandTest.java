package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSubjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUBJECT;
import static seedu.address.testutil.TypicalSubjects.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditSubjectDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.EditSubjectDescriptorBuilder;
import seedu.address.testutil.SubjectBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Subject editedSubject = new SubjectBuilder().build();
        EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder(editedSubject).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUBJECT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUBJECT_SUCCESS, editedSubject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSubject(model.getFilteredSubjectList().get(0), editedSubject);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSubject = Index.fromOneBased(model.getFilteredSubjectList().size());
        Subject lastSubject = model.getFilteredSubjectList().get(indexLastSubject.getZeroBased());

        SubjectBuilder subjectInList = new SubjectBuilder(lastSubject);
        Subject editedSubject = subjectInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastSubject, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUBJECT_SUCCESS, editedSubject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSubject(lastSubject, editedSubject);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUBJECT, new EditSubjectDescriptor());
        Subject editedSubject = model.getFilteredSubjectList().get(INDEX_FIRST_SUBJECT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUBJECT_SUCCESS, editedSubject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSubjectAtIndex(model, INDEX_FIRST_SUBJECT);

        Subject subjectInFilteredList = model.getFilteredSubjectList().get(INDEX_FIRST_SUBJECT.getZeroBased());
        Subject editedSubject = new SubjectBuilder(subjectInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUBJECT,
                new EditSubjectDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SUBJECT_SUCCESS, editedSubject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSubject(model.getFilteredSubjectList().get(0), editedSubject);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSubjectUnfilteredList_failure() {
        Subject firstSubject = model.getFilteredSubjectList().get(INDEX_FIRST_SUBJECT.getZeroBased());
        EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder(firstSubject).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_SUBJECT, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_SUBJECT);
    }

    @Test
    public void execute_duplicateSubjectFilteredList_failure() {
        showSubjectAtIndex(model, INDEX_FIRST_SUBJECT);

        // edit subject in filtered list into a duplicate in address book
        Subject subjectInList = model.getAddressBook().getSubjectList().get(INDEX_SECOND_SUBJECT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUBJECT,
                new EditSubjectDescriptorBuilder(subjectInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_SUBJECT);
    }

    @Test
    public void execute_invalidSubjectIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSubjectList().size() + 1);
        EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidSubjectIndexFilteredList_failure() {
        showSubjectAtIndex(model, INDEX_FIRST_SUBJECT);
        Index outOfBoundIndex = INDEX_SECOND_SUBJECT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getSubjectList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditSubjectDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Subject editedSubject = new SubjectBuilder().build();
        Subject subjectToEdit = model.getFilteredSubjectList().get(INDEX_FIRST_SUBJECT.getZeroBased());
        EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder(editedSubject).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUBJECT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSubject(subjectToEdit, editedSubject);
        expectedModel.commitAddressBook();

        // edit -> first subject edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered subject list to show all subjects
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first subject edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSubjectList().size() + 1);
        EditCommand.EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Subject} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited subject in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the subject object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameSubjectEdited() throws Exception {
        Subject editedSubject = new SubjectBuilder().build();
        EditSubjectDescriptor descriptor = new EditSubjectDescriptorBuilder(editedSubject).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SUBJECT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showSubjectAtIndex(model, INDEX_SECOND_SUBJECT);
        Subject subjectToEdit = model.getFilteredSubjectList().get(INDEX_FIRST_SUBJECT.getZeroBased());
        expectedModel.setSubject(subjectToEdit, editedSubject);
        expectedModel.commitAddressBook();

        // edit -> edits second subject in unfiltered subject list / first subject in filtered subject list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered subject list to show all subjects
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredSubjectList().get(INDEX_FIRST_SUBJECT.getZeroBased()), subjectToEdit);
        // redo -> edits same second subject in unfiltered subject list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_SUBJECT, DESC_AMY);

        // same values -> returns true
        EditSubjectDescriptor copyDescriptor = new EditSubjectDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_SUBJECT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_SUBJECT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_SUBJECT, DESC_BOB)));
    }

}
