package seedu.address.testutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

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
