package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Flashcard's difficulty level in the flash book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {


    public static final String MESSAGE_CONSTRAINTS =
            "Difficulty levels should only contain number 1 (easy), 2 (medium) or 3 (difficult)!";
    public static final String VALIDATION_REGEX = "[1-3]";

    public final String value;

    /**
     * Constructs a {@code Difficulty}.
     *
     * @param difficulty A valid difficulty level.
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_CONSTRAINTS);
        value = difficulty;
    }

    /**
     * Returns true if a given string is a valid difficulty level.
     */
    public static boolean isValidDifficulty(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && value.equals(((Difficulty) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
