package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.SubjectTag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building SubjectTag objects.
 */
public class SubjectTagBuilder {

    private Set<SubjectTag> tags;

    public SubjectTagBuilder() {
        tags = new HashSet<>();
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public SubjectTagBuilder(Flashcard flashcardToCopy) {
        tags = new HashSet<>(flashcardToCopy.getTags());
    }

    /**
     * Parses the {@code tags} into a {@code Set<SubjectTag>} and set it to the {@code Flashcard} that we are building.
     */
    public SubjectTagBuilder withTags(String tag) {
        this.tags = SampleDataUtil.getTagSet(tag);
        return this;
    }


    public SubjectTag build() {
        return new SubjectTag(tags.toString());
    }

}
