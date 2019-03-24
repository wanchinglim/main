package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Content(null));
    }

    @Test
    public void constructor_invalidContent_throwsIllegalArgumentException() {
        String invalidContent = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Content(invalidContent));
    }

    @Test
    public void isValidContent() {
        // null content
        Assert.assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid addresses
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only

        // valid addresses
        assertTrue(Content.isValidContent("Blk 456, Den Road, #01-355"));
        assertTrue(Content.isValidContent("-")); // one character
        assertTrue(Content.isValidContent("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
        // long content
    }
}
