package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.flashcard.Address;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Email;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Flashcard objects.
 */
public class FlashcardBuilder {

    public static final String DEFAULT_TOPIC = "Alice Pauline";
    public static final String DEFAULT_DIFFICULTY = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DEADLINE = "";

    private Topic topic;
    private Difficulty difficulty;
    private Email email;
    private Address address;
    private Deadline deadline;
    private Set<Tag> tags;

    public FlashcardBuilder() {
        topic = new Topic(DEFAULT_TOPIC);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        deadline = new Deadline(DEFAULT_DEADLINE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public FlashcardBuilder(Flashcard flashcardToCopy) {
        topic = flashcardToCopy.getTopic();
        difficulty = flashcardToCopy.getDifficulty();
        email = flashcardToCopy.getEmail();
        address = flashcardToCopy.getAddress();
        deadline = flashcardToCopy.getDeadline();
        tags = new HashSet<>(flashcardToCopy.getTags());
    }

    /**
     * Sets the {@code Topic} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withTopic(String topic) {
        this.topic = new Topic(topic);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Flashcard} that we are building.
     */

    public FlashcardBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    public Flashcard build() {
        return new Flashcard(topic, difficulty, email, address, deadline, tags);
    }

}
