package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOPIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_CHINESE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFlashcards.CHINESE;
//import static seedu.address.testutil.TypicalFlashcards.ENGLISH;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.SubjectTag;
import seedu.address.testutil.FlashcardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Flashcard expectedFlashcard = new FlashcardBuilder(CHINESE).withTags(VALID_TAG_CHINESE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE
                + CONTENT_DESC_CHINESE + TAG_DESC_CHINESE, new AddCommand(expectedFlashcard));

        // multiple names - last topic accepted
        assertParseSuccess(parser, TOPIC_DESC_ENGLISH + TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE
                + CONTENT_DESC_CHINESE + TAG_DESC_CHINESE, new AddCommand(expectedFlashcard));

        // multiple difficulties - last difficulty accepted
        assertParseSuccess(parser, TOPIC_DESC_CHINESE + DIFFICULTY_DESC_ENGLISH + DIFFICULTY_DESC_CHINESE
                + CONTENT_DESC_CHINESE + TAG_DESC_CHINESE, new AddCommand(expectedFlashcard));

        // multiple contents - last content accepted
        assertParseSuccess(parser, TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE + CONTENT_DESC_ENGLISH
                + CONTENT_DESC_CHINESE + TAG_DESC_CHINESE, new AddCommand(expectedFlashcard));
    }

    /*@Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Flashcard expectedFlashcard = new FlashcardBuilder(ENGLISH).withTags().build();
        assertParseSuccess(parser, TOPIC_DESC_ENGLISH + DIFFICULTY_DESC_ENGLISH + CONTENT_DESC_ENGLISH,
                new AddCommand(expectedFlashcard));
    }*/

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing topic prefix
        assertParseFailure(parser, VALID_TOPIC_CHINESE + DIFFICULTY_DESC_CHINESE + CONTENT_DESC_CHINESE,
                expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, TOPIC_DESC_CHINESE + VALID_DIFFICULTY_CHINESE + CONTENT_DESC_CHINESE,
                expectedMessage);

        // missing content prefix
        assertParseFailure(parser, TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE + VALID_CONTENT_CHINESE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TOPIC_CHINESE + VALID_DIFFICULTY_CHINESE + VALID_CONTENT_CHINESE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid topic
        assertParseFailure(parser, INVALID_TOPIC_DESC + DIFFICULTY_DESC_CHINESE + CONTENT_DESC_CHINESE
                + TAG_DESC_ENGLISH + TAG_DESC_CHINESE, Topic.MESSAGE_CONSTRAINTS);

        // invalid difficulty
        assertParseFailure(parser, TOPIC_DESC_CHINESE + INVALID_DIFFICULTY_DESC + CONTENT_DESC_CHINESE
                + TAG_DESC_ENGLISH + TAG_DESC_CHINESE, Difficulty.MESSAGE_CONSTRAINTS);

        // invalid content
        assertParseFailure(parser, TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE + INVALID_CONTENT_DESC
                + TAG_DESC_ENGLISH + TAG_DESC_CHINESE, Content.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE + CONTENT_DESC_CHINESE
                + INVALID_TAG_DESC + VALID_TAG_CHINESE, SubjectTag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TOPIC_DESC + DIFFICULTY_DESC_CHINESE + INVALID_CONTENT_DESC,
                Topic.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TOPIC_DESC_CHINESE + DIFFICULTY_DESC_CHINESE
                + CONTENT_DESC_CHINESE + TAG_DESC_ENGLISH + TAG_DESC_CHINESE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
