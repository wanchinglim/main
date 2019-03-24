package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FlashBook;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.TopicContainsKeywordsPredicate;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TOPIC_AMY = "Amy Bee";
    public static final String VALID_TOPIC_BOB = "Bob Choo";
    public static final String VALID_DIFFICULTY_AMY = "11111111";
    public static final String VALID_DIFFICULTY_BOB = "22222222";
    public static final String VALID_CONTENT_AMY = "Block 312, Amy Street 1";
    public static final String VALID_CONTENT_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final Deadline VALID_DEADLINE_AMY = new Deadline("31 December 2099");
    public static final Deadline VALID_DEADLINE_BOB = new Deadline("31 December 2099");

    public static final String TOPIC_DESC_AMY = " " + PREFIX_TOPIC + VALID_TOPIC_AMY;
    public static final String TOPIC_DESC_BOB = " " + PREFIX_TOPIC + VALID_TOPIC_BOB;
    public static final String DIFFICULTY_DESC_AMY = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_AMY;
    public static final String DIFFICULTY_DESC_BOB = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_BOB;
    public static final String CONTENT_DESC_AMY = " " + PREFIX_CONTENT + VALID_CONTENT_AMY;
    public static final String CONTENT_DESC_BOB = " " + PREFIX_CONTENT + VALID_CONTENT_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_TOPIC_DESC = " " + PREFIX_TOPIC + "James&"; // '&' not allowed in names
    public static final String INVALID_DIFFICULTY_DESC = " " + PREFIX_DIFFICULTY + "911a"; // 'a' not allowed in phones
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_AMY;
    public static final EditCommand.EditFlashcardDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_AMY)
                .withDifficulty(VALID_DIFFICULTY_AMY).withContent(VALID_CONTENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_BOB)
                .withDifficulty(VALID_DIFFICULTY_BOB).withContent(VALID_CONTENT_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the flash book, filtered flashcard list and selected flashcard in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FlashBook expectedFlashBook = new FlashBook(actualModel.getFlashBook());
        List<Flashcard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashcardList());
        Flashcard expectedSelectedFlashcard = actualModel.getSelectedFlashcard();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedFlashBook, actualModel.getFlashBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredFlashcardList());
            assertEquals(expectedSelectedFlashcard, actualModel.getSelectedFlashcard());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the flashcard at the given {@code targetIndex} in the
     * {@code model}'s flash book.
     */
    public static void showFlashcardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Flashcard flashcard = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitName = flashcard.getTopic().fullTopic.split("\\s+");
        model.updateFilteredFlashcardList(new TopicContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }

    /**
     * Deletes the first flashcard in {@code model}'s filtered list from {@code model}'s flash book.
     */
    public static void deleteFirstFlashcard(Model model) {
        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(0);
        model.deleteFlashcard(firstFlashcard);
        model.commitFlashBook();
    }

}
