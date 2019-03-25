package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.PreviousCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Subject;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns the next Index number.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseNextIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        NextCommand.getNextInteger(Integer.parseInt(trimmedIndex) + 1);
        return Index.fromOneBased(Integer.parseInt(trimmedIndex) + 1);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns the previous Index number.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parsePreviousIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        PreviousCommand.getPreviousInteger(Integer.parseInt(trimmedIndex) - 1);
        return Index.fromOneBased(Integer.parseInt(trimmedIndex) - 1);
    }

    /**
     * Parses a {@code String topic} into a {@code Topic}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code topic} is invalid.
     */
    public static Topic parseTopic(String topic) throws ParseException {
        requireNonNull(topic);
        String trimmedTopic = topic.trim();

        if (!Topic.isValidTopic(trimmedTopic)) {
            throw new ParseException(Topic.MESSAGE_CONSTRAINTS);
        }
        return new Topic(trimmedTopic);
    }

    /**
     * Parses a {@code String difficulty} into a {@code Difficulty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code difficulty} is invalid.
     */
    public static Difficulty parseDifficulty(String difficulty) throws ParseException {
        requireNonNull(difficulty);
        String trimmedDifficulty = difficulty.trim();
        if (!Difficulty.isValidDifficulty(trimmedDifficulty)) {
            throw new ParseException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        return new Difficulty(trimmedDifficulty);
    }

    /**
     * Parses a {@code String content} into an {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Content.MESSAGE_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }

    /**
     * Parses a {@code String tag} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Subject parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Subject.isValidSubjectName(trimmedTag)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Subject>}.
     */
    public static Set<Subject> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Subject> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
