package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Flashcard's topic in the flash book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTopic(String)}
 */
public class Topic {

    public static final String MESSAGE_CONSTRAINTS =
            "Topics should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the topic must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTopic;

    /**
     * Constructs a {@code Topic}.
     *
     * @param topic A valid topic.
     */
    public Topic(String topic) {
        requireNonNull(topic);
        checkArgument(isValidTopic(topic), MESSAGE_CONSTRAINTS);
        fullTopic = topic;
    }

    /**
     * Returns true if a given string is a valid topic.
     */
    public static boolean isValidTopic(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullTopic;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Topic // instanceof handles nulls
                && fullTopic.equals(((Topic) other).fullTopic)); // state check
    }

    @Override
    public int hashCode() {
        return fullTopic.hashCode();
    }

}
