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
    public void constructor_invalidTopic_throwsIllegalArgumentException() {
        String invalidTopic = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Topic(invalidTopic));
    }

    @Test
    public void isValidTopic() {
        // null topic
        Assert.assertThrows(NullPointerException.class, () -> Topic.isValidTopic(null));

        // invalid topic
        assertFalse(Topic.isValidTopic("")); // empty string
        assertFalse(Topic.isValidTopic(" ")); // spaces only
        assertFalse(Topic.isValidTopic("^")); // only non-alphanumeric characters
        assertFalse(Topic.isValidTopic("peter*")); // contains non-alphanumeric characters

        // valid topic
        assertTrue(Topic.isValidTopic("peter jack")); // alphabets only
        assertTrue(Topic.isValidTopic("12345")); // numbers only
        assertTrue(Topic.isValidTopic("peter the 2nd")); // alphanumeric characters
        assertTrue(Topic.isValidTopic("Capital Tan")); // with capital letters
        assertTrue(Topic.isValidTopic("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
