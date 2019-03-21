package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TopicTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Topic(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Topic(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Topic.isValidName(null));

        // invalid name
        assertFalse(Topic.isValidName("")); // empty string
        assertFalse(Topic.isValidName(" ")); // spaces only
        assertFalse(Topic.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Topic.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Topic.isValidName("peter jack")); // alphabets only
        assertTrue(Topic.isValidName("12345")); // numbers only
        assertTrue(Topic.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Topic.isValidName("Capital Tan")); // with capital letters
        assertTrue(Topic.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
