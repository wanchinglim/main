package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalSubjects.getTypicalSubjectBook;

import java.util.Arrays;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.model.FlashBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.TopicContainsKeywordsPredicate;
import seedu.address.model.tag.SubjectTag;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;
import seedu.address.testutil.FlashcardBuilder;
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Flashcard editedFlashcard = new FlashcardBuilder().build();
        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);
        model.setSelectedSubject(new SubjectTag("english"));

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(model.getSubjectBook(),
                new FlashBook(model.getFlashBook()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(0), editedFlashcard);
        expectedModel.commitFlashBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        model.setSelectedSubject(new SubjectTag("english"));
        ObservableList<Flashcard> updatedFlashcardList = model.getUpdatedFlashcardList();
        model.setSelectedSubject(new SubjectTag("english"));

        Index indexLastSubject = Index.fromOneBased(model.getFilteredFlashcardList().size());
        Flashcard lastFlashcard = model.getFilteredFlashcardList().get(indexLastSubject.getZeroBased());
        int atUpdated = updatedFlashcardList.indexOf(lastFlashcard);

        FlashcardBuilder subjectInList = new FlashcardBuilder(lastFlashcard);

        Flashcard editedFlashcard = subjectInList
                .withTopic(VALID_TOPIC_CHINESE)
                .withDifficulty(VALID_DIFFICULTY_CHINESE)
                .withTags(VALID_TAG_ENGLISH)
                .build();

        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
                .withTopic(VALID_TOPIC_CHINESE)
                .withDifficulty(VALID_DIFFICULTY_CHINESE)
                .withTags(VALID_TAG_ENGLISH)
                .build();
        EditCommand editCommand = new EditCommand(Index.fromZeroBased(atUpdated), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(model.getSubjectBook(),
                new FlashBook(model.getFlashBook()), new UserPrefs());
        expectedModel.setSelectedSubject(new SubjectTag("english"));
        expectedModel.setFlashcard(lastFlashcard, editedFlashcard);
        expectedModel.commitFlashBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, new EditCommand.EditFlashcardDescriptor());
        model.setSelectedSubject(new SubjectTag("english"));
        Flashcard editedFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(model.getSubjectBook(),
                new FlashBook(model.getFlashBook()), new UserPrefs());
        expectedModel.commitFlashBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("english"));

        Flashcard flashcardInFilteredList = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard editedFlashcard = new FlashcardBuilder(flashcardInFilteredList)
                .withTopic(VALID_TOPIC_CHINESE).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_CHINESE).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);
        Model expectedModel = new ModelManager(model.getSubjectBook(),
                new FlashBook(model.getFlashBook()), new UserPrefs());

        final String[] splitName = flashcardInFilteredList.getTopic().fullTopic.split("\\s+");
        expectedModel.updateFilteredFlashcardList(new TopicContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(0), editedFlashcard);
        expectedModel.commitFlashBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFlashcardUnfilteredList_failure() {
        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(firstFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_FLASHCARD, descriptor);
        model.setSelectedSubject(new SubjectTag("english"));
        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_duplicateFlashcardFilteredList_failure() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("english"));

        // edit flashcard in filtered list into a duplicate in flash book
        Flashcard flashcardInList = model.getFlashBook().getFlashcardList()
                .get(INDEX_SECOND_FLASHCARD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashcardDescriptorBuilder(flashcardInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    // problem
    @Test
    public void execute_invalidFlashcardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
                .withTopic(VALID_TOPIC_CHINESE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
        model.setSelectedSubject(new SubjectTag("english"));

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of flash book
     */
    @Test
    public void execute_invalidFlashcardIndexFilteredList_failure() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        model.setSelectedSubject(new SubjectTag("english"));
        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of flash book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFlashBook().getFlashcardList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_CHINESE).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Flashcard editedFlashcard = new FlashcardBuilder().build();
        Flashcard flashcardToEdit = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);
        model.setSelectedSubject(new SubjectTag("english"));
        Model expectedModel = new ModelManager(model.getSubjectBook(),
                new FlashBook(model.getFlashBook()), new UserPrefs());
        expectedModel.setFlashcard(flashcardToEdit, editedFlashcard);
        expectedModel.commitFlashBook();

        // edit -> first flashcard edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts flashbook back to previous state and filtered flashcard list to show all subjects
        expectedModel.undoFlashBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first flashcard edited again
        expectedModel.redoFlashBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
                .withTopic(VALID_TOPIC_CHINESE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
        model.setSelectedSubject(new SubjectTag("english"));

        // execution failed -> flash book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        // single flash book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Flashcard} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited flashcard in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the flashcard object regardless of indexing.
     */
    // problem
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameFlashcardEdited() throws Exception {
        model.setSelectedSubject(new SubjectTag("chinese"));

        Flashcard editedFlashcard = new FlashcardBuilder().build();
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);
        Model expectedModel = new ModelManager(model.getSubjectBook(),
                new FlashBook(model.getFlashBook()), new UserPrefs());

        showFlashcardAtIndex(model, INDEX_SECOND_FLASHCARD);
        Flashcard flashcardToEdit = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());

        final String[] splitName = flashcardToEdit.getTopic().fullTopic.split("\\s+");
        expectedModel.updateFilteredFlashcardList(new TopicContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        expectedModel.setFlashcard(flashcardToEdit, editedFlashcard);
        expectedModel.commitFlashBook();
        expectedModel.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // edit -> edits second flashcard in unfiltered flashcard list / first flashcard in filtered flashcard list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered flashcard list to show all subjects
        expectedModel.undoFlashBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased()), flashcardToEdit);
        // redo -> edits same second flashcard in unfiltered flashcard list
        expectedModel.redoFlashBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_FLASHCARD, DESC_ENGLISH);

        // same values -> returns true
        EditFlashcardDescriptor copyDescriptor = new EditFlashcardDescriptor(DESC_ENGLISH);
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
    }

}
