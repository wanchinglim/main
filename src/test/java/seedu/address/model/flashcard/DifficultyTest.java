package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DifficultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Difficulty(null));
    }

    @Test
    public void constructor_invalidDifficulty_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Difficulty(invalidPhone));
    }

    @Test
    public void isValidDifficulty() {
        // null difficulty level
        Assert.assertThrows(NullPointerException.class, () -> Difficulty.isValidDifficulty(null));

        // invalid difficulty levels
        assertFalse(Difficulty.isValidDifficulty("")); // empty string
        assertFalse(Difficulty.isValidDifficulty(" ")); // spaces only
        assertFalse(Difficulty.isValidDifficulty("91")); // less than 3 numbers
        assertFalse(Difficulty.isValidDifficulty("phone")); // non-numeric
        assertFalse(Difficulty.isValidDifficulty("9011p041")); // alphabets within digits
        assertFalse(Difficulty.isValidDifficulty("9312 1534")); // spaces within digits

        // valid difficulty levels
        assertTrue(Difficulty.isValidDifficulty("911")); // exactly 3 numbers
        assertTrue(Difficulty.isValidDifficulty("93121534"));
        assertTrue(Difficulty.isValidDifficulty("124293842033123")); // long phone numbers
    }
}
