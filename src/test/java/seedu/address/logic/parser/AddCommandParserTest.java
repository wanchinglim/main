package seedu.address.logic.parser;

/**import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFlashCards.CHINESE;
import static seedu.address.testutil.TypicalFlashCards.ENGLISH;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashCardBuilder;**/

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    /**
    @Test
    public void parse_allFieldsPresent_success() {
        FlashCard expectedFlashCard = new FlashCardBuilder(CHINESE).withTags(VALID_TAG_CHINESE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
            + TAG_DESC_CHINESE, new AddCommand(expectedFlashCard));

        // multiple subjects - last subject accepted
        assertParseSuccess(parser, SUBJECT_DESC_ENGLISH + SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
            + TAG_DESC_CHINESE, new AddCommand(expectedFlashCard));

        // multiple contents - last content accepted
        assertParseSuccess(parser, SUBJECT_DESC_CHINESE + CONTENT_DESC_ENGLISH + CONTENT_DESC_CHINESE
            + TAG_DESC_CHINESE, new AddCommand(expectedFlashCard));

        // multiple tags - all accepted
        FlashCard expectedFlashCardMultipleTags = new FlashCardBuilder(CHINESE).withTags(VALID_TAG_CHINESE,
            VALID_TAG_ENGLISH).build();
        assertParseSuccess(parser, SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
            + TAG_DESC_ENGLISH + TAG_DESC_CHINESE, new AddCommand(expectedFlashCardMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        FlashCard expectedFlashCard = new FlashCardBuilder(ENGLISH).withTags().build();
        assertParseSuccess(parser, SUBJECT_DESC_ENGLISH + CONTENT_DESC_ENGLISH,
                new AddCommand(expectedFlashCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing subject prefix
        assertParseFailure(parser, VALID_SUBJECT_CHINESE + CONTENT_DESC_CHINESE + TAG_DESC_CHINESE,
            expectedMessage);

        // missing content prefix
        assertParseFailure(parser, SUBJECT_DESC_CHINESE + VALID_CONTENT_CHINESE + TAG_DESC_CHINESE,
            expectedMessage);

        // missing tag prefix
        assertParseFailure(parser, SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE + VALID_TAG_CHINESE,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_SUBJECT_CHINESE + VALID_CONTENT_CHINESE + VALID_TAG_CHINESE,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid subject
        assertParseFailure(parser, INVALID_SUBJECT_DESC + CONTENT_DESC_CHINESE
            + TAG_DESC_ENGLISH + TAG_DESC_CHINESE, Subject.MESSAGE_CONSTRAINTS);

        // invalid content
        assertParseFailure(parser, SUBJECT_DESC_CHINESE + INVALID_CONTENT_DESC
            + TAG_DESC_ENGLISH + TAG_DESC_CHINESE, Content.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
            + INVALID_TAG_DESC + VALID_TAG_CHINESE, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + SUBJECT_DESC_CHINESE + CONTENT_DESC_CHINESE
            + TAG_DESC_ENGLISH + TAG_DESC_CHINESE,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }*/
}
