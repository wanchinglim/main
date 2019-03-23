package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.flashcard.Address;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Email;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Phone;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Flashcard objects.
 */
public class FlashcardBuilder {

    public static final String DEFAULT_TOPIC = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DEADLINE = "";

    private Topic topic;
    private Phone phone;
    private Email email;
    private Address address;
    private Deadline deadline;
    private Set<Tag> tags;

    public FlashcardBuilder() {
        topic = new Topic(DEFAULT_TOPIC);
        phone = new Phone(DEFAULT_PHONE);
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
        phone = flashcardToCopy.getPhone();
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
     * Sets the {@code Phone} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
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
        return new Flashcard(topic, phone, email, address, deadline, tags);
    }

}
