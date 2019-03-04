package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.flashcard.ContentContainsKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_SUBJECT_ENGLISH = "English";
    public static final String VALID_SUBJECT_CHINESE = "Chinese";
    public static final String VALID_CONTENT_ENGLISH = "The quick brown fox jumps over the lazy dog.";
    public static final String VALID_CONTENT_CHINESE = "Ni hao ma?";
    public static final String VALID_TAG_ENGLISH = "english";
    public static final String VALID_TAG_CHINESE = "chinese";

    public static final String SUBJECT_DESC_ENGLISH = " " + PREFIX_SUBJECT + VALID_SUBJECT_ENGLISH;
    public static final String SUBJECT_DESC_CHINESE = " " + PREFIX_SUBJECT + VALID_SUBJECT_CHINESE;
    public static final String CONTENT_DESC_ENGLISH = " " + PREFIX_CONTENT + VALID_CONTENT_ENGLISH;
    public static final String CONTENT_DESC_CHINESE = " " + PREFIX_CONTENT + VALID_CONTENT_CHINESE;
    public static final String TAG_DESC_CHINESE = " " + PREFIX_TAG + VALID_TAG_CHINESE;
    public static final String TAG_DESC_ENGLISH = " " + PREFIX_TAG + VALID_TAG_ENGLISH;

    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "James&"; // '&' not allowed in names
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashCardDescriptor DESC_ENGLISH;
    public static final EditCommand.EditFlashCardDescriptor DESC_CHINESE;

    static {
        DESC_ENGLISH = new EditFlashCardDescriptorBuilder().withSubject(VALID_SUBJECT_ENGLISH)
                .withContent(VALID_CONTENT_ENGLISH)
                .withTags(VALID_TAG_CHINESE).build();
        DESC_CHINESE = new EditFlashCardDescriptorBuilder().withSubject(VALID_SUBJECT_CHINESE)
                .withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_ENGLISH, VALID_TAG_CHINESE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered flashcard list and selected flashcard in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<FlashCard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashCardList());
        FlashCard expectedSelectedFlashCard = actualModel.getSelectedFlashCard();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredFlashCardList());
            assertEquals(expectedSelectedFlashCard, actualModel.getSelectedFlashCard());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the flashcard at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFlashCardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashCardList().size());

        FlashCard flashcard = model.getFilteredFlashCardList().get(targetIndex.getZeroBased());
        final String[] splitSubject = flashcard.getSubject().subject.split("\\s+");
        model.updateFilteredFlashCardList(new ContentContainsKeywordsPredicate(Arrays.asList(splitSubject[0])));

        assertEquals(1, model.getFilteredFlashCardList().size());
    }

    /**
     * Deletes the first flashcard in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstFlashCard(Model model) {
        FlashCard firstFlashCard = model.getFilteredFlashCardList().get(0);
        model.deleteFlashCard(firstFlashCard);
        model.commitAddressBook();
    }

}
