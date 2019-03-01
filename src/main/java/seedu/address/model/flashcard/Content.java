package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Content {

    public static final String MESSAGE_CONSTRAINTS =
            "Content should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String content;

    /**
     * Constructs a {@code Name}.
     *
     * @param cont A valid name.
     */
    public Content(String cont) {
        requireNonNull(cont);
        checkArgument(isValidName(cont), MESSAGE_CONSTRAINTS);
        content = cont;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && content.equals(((Content) other).content)); // state check
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
