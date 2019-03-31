package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a SubjectTag in the flash book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSubjectName(String)}
 */
public class SubjectTag {

    public static final String MESSAGE_CONSTRAINTS = "Subjects names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String subjectName;

    /**
     * Constructs a {@code SubjectTag}.
     *
     * @param subjectName A valid subject name.
     */
    public SubjectTag(String subjectName) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_CONSTRAINTS);
        this.subjectName = subjectName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidSubjectName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubjectTag // instanceof handles nulls
                && subjectName.equals(((SubjectTag) other).subjectName)); // state check
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return subjectName;
    }

    /**
     * Returns true if both subjects have the same field.
     * This defines a stronger notion of equality between two subjects.
     */
    public boolean isSameSubject(SubjectTag otherSubject) {
        if (otherSubject == this) {
            return true;
        }
        return otherSubject != null
                && otherSubject.toString().equals(subjectName);
    }
}
