package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Subject;

/**
 * A utility class to help with building EditFlashcardDescriptor objects.
 */
public class EditFlashcardDescriptorBuilder {

    private EditFlashcardDescriptor descriptor;

    public EditFlashcardDescriptorBuilder() {
        descriptor = new EditCommand.EditFlashcardDescriptor();
    }

    public EditFlashcardDescriptorBuilder(EditFlashcardDescriptor descriptor) {
        this.descriptor = new EditCommand.EditFlashcardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFlashcardDescriptor} with fields containing {@code flashcard}'s details
     */
    public EditFlashcardDescriptorBuilder(Flashcard flashcard) {
        descriptor = new EditCommand.EditFlashcardDescriptor();
        descriptor.setTopic(flashcard.getTopic());
        descriptor.setDifficulty(flashcard.getDifficulty());
        descriptor.setContent(flashcard.getContent());
        descriptor.setTags(flashcard.getTags());
    }

    /**
     * Sets the {@code Topic} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withTopic(String topic) {
        descriptor.setTopic(new Topic(topic));
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withDifficulty(String difficulty) {
        descriptor.setDifficulty(new Difficulty(difficulty));
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withContent(String content) {
        descriptor.setContent(new Content(content));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Subject>} and set it to the {@code EditFlashcardDescriptor}
     * that we are building.
     */
    public EditFlashcardDescriptorBuilder withTags(String... tags) {
        Set<Subject> tagSet = Stream.of(tags).map(Subject::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditFlashcardDescriptor build() {
        return descriptor;
    }
}
