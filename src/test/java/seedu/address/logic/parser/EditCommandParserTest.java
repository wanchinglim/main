package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOPIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Subject;

import seedu.address.testutil.EditFlashcardDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_SUBJECT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TOPIC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TOPIC_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TOPIC_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TOPIC_DESC, Topic.MESSAGE_CONSTRAINTS); // invalid topic
        assertParseFailure(parser, "1" + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_CONSTRAINTS);
        // invalid difficulty
        assertParseFailure(parser, "1" + INVALID_CONTENT_DESC, Content.MESSAGE_CONSTRAINTS); // invalid content
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Subject.MESSAGE_CONSTRAINTS); // invalid tag

        // valid difficulty followed by invalid difficulty. The test case for invalid difficulty
        // followed by valid difficulty
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DIFFICULTY_DESC_BOB + INVALID_DIFFICULTY_DESC,
                Difficulty.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SUBJECT} alone will reset the tags of the {@code Flashcard} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Subject.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Subject.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Subject.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TOPIC_DESC + VALID_CONTENT_AMY
                        + VALID_DIFFICULTY_AMY, Topic.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FLASHCARD;
        String userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_BOB + TAG_DESC_HUSBAND
                + CONTENT_DESC_AMY + TOPIC_DESC_AMY + TAG_DESC_FRIEND;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_AMY)
                .withDifficulty(VALID_DIFFICULTY_BOB).withContent(VALID_CONTENT_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_BOB + CONTENT_DESC_AMY;

        EditCommand.EditFlashcardDescriptor descriptor =
                new EditFlashcardDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BOB)
                .withContent(VALID_CONTENT_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // topic
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + TOPIC_DESC_AMY;
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withTopic(VALID_TOPIC_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // difficulty
        userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_AMY;
        descriptor = new EditFlashcardDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // content
        userInput = targetIndex.getOneBased() + CONTENT_DESC_AMY;
        descriptor = new EditFlashcardDescriptorBuilder().withContent(VALID_CONTENT_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditFlashcardDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_AMY + CONTENT_DESC_AMY
                + TAG_DESC_FRIEND + DIFFICULTY_DESC_AMY + CONTENT_DESC_AMY + TAG_DESC_FRIEND
                + DIFFICULTY_DESC_BOB + CONTENT_DESC_BOB + TAG_DESC_HUSBAND;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BOB)
                .withContent(VALID_CONTENT_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + INVALID_DIFFICULTY_DESC + DIFFICULTY_DESC_BOB;
        EditFlashcardDescriptor descriptor =
                new EditFlashcardDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DIFFICULTY_DESC + CONTENT_DESC_BOB
                + DIFFICULTY_DESC_BOB;
        descriptor =
                new EditFlashcardDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_BOB)
                        .withContent(VALID_CONTENT_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
