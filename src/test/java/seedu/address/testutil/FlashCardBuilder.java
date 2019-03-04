package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building FlashCard objects.
 */
public class FlashCardBuilder {

    public static final String DEFAULT_SUBJECT = "English";
    public static final String DEFAULT_CONTENT = "The quick brown fox jumps over the lazy dog.";

    private Subject sub;
    private Content cont;
    private Set<Tag> tags;

    public FlashCardBuilder() {
        sub = new Subject(DEFAULT_SUBJECT);
        cont = new Content(DEFAULT_CONTENT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the FlashCardBuilder with the data of {@code flashcardToCopy}.
     */
    public FlashCardBuilder(FlashCard flashcardToCopy) {
        sub = flashcardToCopy.getSubject();
        cont = flashcardToCopy.getContent();
        tags = new HashSet<>(flashcardToCopy.getTags());
    }

    /**
     * Sets the {@code Subject} of the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withSubject(String sub) {
        this.sub = new Subject(sub);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withContent(String cont) {
        this.cont = new Content(cont);
        return this;
    }

    public FlashCard build() {
        return new FlashCard(sub, cont, tags);
    }

}
