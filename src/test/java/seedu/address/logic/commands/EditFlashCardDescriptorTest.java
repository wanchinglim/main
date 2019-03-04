package seedu.address.logic.commands;
/**
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;
**/
public class EditFlashCardDescriptorTest {
    /**
    @Test
    public void equals() {
        // same values -> returns true
        EditFlashCardDescriptor descriptorWithSameValues = new EditFlashCardDescriptor(DESC_ENGLISH);
        assertTrue(DESC_ENGLISH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ENGLISH.equals(DESC_ENGLISH));

        // null -> returns false
        assertFalse(DESC_ENGLISH.equals(null));

        // different types -> returns false
        assertFalse(DESC_ENGLISH.equals(5));

        // different values -> returns false
        assertFalse(DESC_ENGLISH.equals(DESC_CHINESE));

        // different subject -> returns false
        EditFlashCardDescriptor editedAmy = new EditFlashCardDescriptorBuilder(DESC_ENGLISH)
                .withSubject(VALID_SUBJECT_CHINESE).build();
        assertFalse(DESC_ENGLISH.equals(editedAmy));

        // different content -> returns false
        editedAmy = new EditFlashCardDescriptorBuilder(DESC_ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        assertFalse(DESC_ENGLISH.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditFlashCardDescriptorBuilder(DESC_ENGLISH).withTags(VALID_TAG_ENGLISH).build();
        assertFalse(DESC_ENGLISH.equals(editedAmy));
    }**/
}
