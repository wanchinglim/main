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
        // null cont
        Assert.assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid cont
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only
        assertFalse(Content.isValidContent("^")); // only non-alphanumeric characters
        assertFalse(Content.isValidContent("peter*")); // contains non-alphanumeric characters

        // valid cont
        assertTrue(Content.isValidContent("peter jack")); // alphabets only
        assertTrue(Content.isValidContent("12345")); // numbers only
        assertTrue(Content.isValidContent("peter the 2nd")); // alphanumeric characters
        assertTrue(Content.isValidContent("Capital Tan")); // with capital letters
        assertTrue(Content.isValidContent("David Roger Jackson Ray Jr 2nd")); // long conts
    }
}
