package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FlashBook;
import seedu.address.model.Model;
//import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.TopicContainsKeywordsPredicate;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TOPIC_ENGLISH = "Brown Fox";
    public static final String VALID_TOPIC_CHINESE = "Ni hao ma";
    public static final String VALID_DIFFICULTY_ENGLISH = "1";
    public static final String VALID_DIFFICULTY_CHINESE = "2";
    public static final String VALID_CONTENT_ENGLISH = "The quick brown fox jumps over the lazy dog";
    public static final String VALID_CONTENT_CHINESE = "Hao";
    public static final String VALID_TAG_ENGLISH = "english";
    public static final String VALID_TAG_CHINESE = "chinese";
    //public static final Deadline VALID_DEADLINE_AMY = new Deadline("31 December 2099");
    //public static final Deadline VALID_DEADLINE_BOB = new Deadline("31 December 2099");

    public static final String TOPIC_DESC_ENGLISH = " " + PREFIX_TOPIC + VALID_TOPIC_ENGLISH;
    public static final String TOPIC_DESC_CHINESE = " " + PREFIX_TOPIC + VALID_TOPIC_CHINESE;
    public static final String DIFFICULTY_DESC_ENGLISH = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_ENGLISH;
    public static final String DIFFICULTY_DESC_CHINESE = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_CHINESE;
    public static final String CONTENT_DESC_ENGLISH = " " + PREFIX_CONTENT + VALID_CONTENT_ENGLISH;
    public static final String CONTENT_DESC_CHINESE = " " + PREFIX_CONTENT + VALID_CONTENT_CHINESE;
    public static final String TAG_DESC_CHINESE = " " + PREFIX_SUBJECT + VALID_TAG_CHINESE;
    public static final String TAG_DESC_ENGLISH = " " + PREFIX_SUBJECT + VALID_TAG_ENGLISH;

    public static final String INVALID_TOPIC_DESC = " " + PREFIX_TOPIC + "English&"; // '&' not allowed in names
    public static final String INVALID_DIFFICULTY_DESC = " " + PREFIX_DIFFICULTY + "2a"; // 'a' not allowed in phones
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_SUBJECT + "english*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_ENGLISH;
    public static final EditCommand.EditFlashcardDescriptor DESC_CHINESE;

    static {
        DESC_ENGLISH = new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_ENGLISH)
                .withDifficulty(VALID_DIFFICULTY_ENGLISH).withContent(VALID_CONTENT_ENGLISH)
                .withTags(VALID_TAG_CHINESE).build();
        DESC_CHINESE = new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_CHINESE)
                .withDifficulty(VALID_DIFFICULTY_CHINESE).withContent(VALID_CONTENT_CHINESE)
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
