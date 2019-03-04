package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
/**import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;**/
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
/**import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;
import org.junit.Test;
import seedu.address.commons.core.index.Index;**/

import seedu.address.logic.commands.EditCommand;
/**import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;**/

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    /**
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SUBJECT_ENGLISH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + SUBJECT_DESC_ENGLISH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + SUBJECT_DESC_ENGLISH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_CONTENT_DESC, Content.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_CONTENT_DESC + VALID_TAG_ENGLISH,
            Content.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + CONTENT_DESC_CHINESE + INVALID_CONTENT_DESC,
            Content.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code FlashCard} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_CHINESE + TAG_DESC_ENGLISH + TAG_EMPTY,
            Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_CHINESE + TAG_EMPTY + TAG_DESC_ENGLISH,
            Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_CHINESE + TAG_DESC_ENGLISH,
            Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_SUBJECT_DESC + VALID_CONTENT_ENGLISH,
                Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FLASHCARD;
        String userInput = targetIndex.getOneBased() + CONTENT_DESC_CHINESE + TAG_DESC_ENGLISH
            + SUBJECT_DESC_ENGLISH + TAG_DESC_CHINESE;

        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder().withSubject(VALID_SUBJECT_ENGLISH)
            .withContent(VALID_CONTENT_CHINESE).withTags(VALID_TAG_ENGLISH, VALID_TAG_CHINESE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + CONTENT_DESC_CHINESE;

        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder().withContent(VALID_CONTENT_CHINESE)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + SUBJECT_DESC_ENGLISH;
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder()
                .withSubject(VALID_SUBJECT_ENGLISH).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + CONTENT_DESC_ENGLISH;
        descriptor = new EditFlashCardDescriptorBuilder().withContent(VALID_CONTENT_ENGLISH).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_CHINESE;
        descriptor = new EditFlashCardDescriptorBuilder().withTags(VALID_TAG_CHINESE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + CONTENT_DESC_ENGLISH
            + TAG_DESC_CHINESE + CONTENT_DESC_ENGLISH + TAG_DESC_CHINESE
            + CONTENT_DESC_CHINESE + TAG_DESC_ENGLISH;

        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder().withContent(VALID_CONTENT_CHINESE)
                .withTags(VALID_TAG_CHINESE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + INVALID_CONTENT_DESC + CONTENT_DESC_CHINESE;
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder()
                .withContent(VALID_CONTENT_CHINESE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_CONTENT_DESC + CONTENT_DESC_CHINESE;
        descriptor = new EditFlashCardDescriptorBuilder().withContent(VALID_CONTENT_CHINESE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }**/
}
