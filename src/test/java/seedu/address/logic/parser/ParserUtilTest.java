package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Subject;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_TOPIC = "R@chel";
    private static final String INVALID_DIFFICULTY = "+651234";
    private static final String INVALID_CONTENT = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TOPIC = "Rachel Walker";
    private static final String VALID_DIFFICULTY = "123456";
    private static final String VALID_CONTENT = "123 Main Street #0505";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_FLASHCARD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_FLASHCARD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTopic_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTopic((String) null));
    }

    @Test
    public void parseTopic_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTopic(INVALID_TOPIC));
    }

    @Test
    public void parseTopic_validValueWithoutWhitespace_returnTopic() throws Exception {
        Topic expectedTopic = new Topic(VALID_TOPIC);
        assertEquals(expectedTopic, ParserUtil.parseTopic(VALID_TOPIC));
    }

    @Test
    public void parseTopic_validValueWithWhitespace_returnsTrimmedTopic() throws Exception {
        String topicWithWhitespace = WHITESPACE + VALID_TOPIC + WHITESPACE;
        Topic expectedTopic = new Topic(VALID_TOPIC);
        assertEquals(expectedTopic, ParserUtil.parseTopic(topicWithWhitespace));
    }

    @Test
    public void parseDifficulty_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDifficulty((String) null));
    }

    @Test
    public void parseDifficulty_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDifficulty(INVALID_DIFFICULTY));
    }

    @Test
    public void parseDifficulty_validValueWithoutWhitespace_returnsDifficulty() throws Exception {
        Difficulty expectedDifficulty = new Difficulty(VALID_DIFFICULTY);
        assertEquals(expectedDifficulty, ParserUtil.parseDifficulty(VALID_DIFFICULTY));
    }

    @Test
    public void parseDifficulty_validValueWithWhitespace_returnsTrimmedDifficulty() throws Exception {
        String difficultyWithWhitespace = WHITESPACE + VALID_DIFFICULTY + WHITESPACE;
        Difficulty expectedDifficulty = new Difficulty(VALID_DIFFICULTY);
        assertEquals(expectedDifficulty, ParserUtil.parseDifficulty(difficultyWithWhitespace));
    }

    @Test
    public void parseContent_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseContent((String) null));
    }

    @Test
    public void parseContent_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseContent(INVALID_CONTENT));
    }

    @Test
    public void parseContent_validValueWithoutWhitespace_returnsContent() throws Exception {
        Content expectedContent = new Content(VALID_CONTENT);
        assertEquals(expectedContent, ParserUtil.parseContent(VALID_CONTENT));
    }

    @Test
    public void parseContent_validValueWithWhitespace_returnsTrimmedContent() throws Exception {
        String contentWithWhitespace = WHITESPACE + VALID_CONTENT + WHITESPACE;
        Content expectedContent = new Content(VALID_CONTENT);
        assertEquals(expectedContent, ParserUtil.parseContent(contentWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Subject expectedTag = new Subject(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Subject expectedTag = new Subject(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Subject> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Subject> expectedTagSet = new HashSet<Subject>(Arrays.asList(new Subject(VALID_TAG_1),
            new Subject(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
