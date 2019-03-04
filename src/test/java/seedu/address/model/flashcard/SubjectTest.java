package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null sub
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid sub
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("^")); // only non-alphanumeric characters
        assertFalse(Subject.isValidSubject("peter*")); // contains non-alphanumeric characters

        // valid sub
        assertTrue(Subject.isValidSubject("peter jack")); // alphabets only
        assertTrue(Subject.isValidSubject("12345")); // numbers only
        assertTrue(Subject.isValidSubject("peter the 2nd")); // alphanumeric characters
        assertTrue(Subject.isValidSubject("Capital Tan")); // with capital letters
        assertTrue(Subject.isValidSubject("David Roger Jackson Ray Jr 2nd")); // long subs
    }
}
