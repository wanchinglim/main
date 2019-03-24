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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFlashcards.AMY;
import static seedu.address.testutil.TypicalFlashcards.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashcardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Flashcard expectedFlashcard = new FlashcardBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFlashcard));

        // multiple names - last topic accepted
        assertParseSuccess(parser, TOPIC_DESC_AMY + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFlashcard));

        // multiple difficulties - last difficulty accepted
        assertParseSuccess(parser, TOPIC_DESC_BOB + DIFFICULTY_DESC_AMY + DIFFICULTY_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFlashcard));

        // multiple contents - last content accepted
        assertParseSuccess(parser, TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB + CONTENT_DESC_AMY
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFlashcard));

        // multiple tags - all accepted
        Flashcard expectedFlashcardMultipleTags =
                new FlashcardBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB + CONTENT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedFlashcardMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Flashcard expectedFlashcard = new FlashcardBuilder(AMY).withTags().build();
        assertParseSuccess(parser, TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + CONTENT_DESC_AMY,
                new AddCommand(expectedFlashcard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing topic prefix
        assertParseFailure(parser, VALID_TOPIC_BOB + DIFFICULTY_DESC_BOB + CONTENT_DESC_BOB,
                expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, TOPIC_DESC_BOB + VALID_DIFFICULTY_BOB + CONTENT_DESC_BOB,
                expectedMessage);

        // missing content prefix
        assertParseFailure(parser, TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB + VALID_CONTENT_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TOPIC_BOB + VALID_DIFFICULTY_BOB + VALID_CONTENT_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid topic
        assertParseFailure(parser, INVALID_TOPIC_DESC + DIFFICULTY_DESC_BOB + CONTENT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Topic.MESSAGE_CONSTRAINTS);

        // invalid difficulty
        assertParseFailure(parser, TOPIC_DESC_BOB + INVALID_DIFFICULTY_DESC + CONTENT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Difficulty.MESSAGE_CONSTRAINTS);

        // invalid content
        assertParseFailure(parser, TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB + INVALID_CONTENT_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Content.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB + CONTENT_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TOPIC_DESC + DIFFICULTY_DESC_BOB + INVALID_CONTENT_DESC,
                Topic.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
