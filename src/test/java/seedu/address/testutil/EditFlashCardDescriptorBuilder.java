package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditFlashCardDescriptor objects.
 */
public class EditFlashCardDescriptorBuilder {

    private EditFlashCardDescriptor descriptor;

    public EditFlashCardDescriptorBuilder() {
        descriptor = new EditFlashCardDescriptor();
    }

    public EditFlashCardDescriptorBuilder(EditFlashCardDescriptor descriptor) {
        this.descriptor = new EditFlashCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFlashCardDescriptor} with fields containing {@code flashcard}'s details
     */
    public EditFlashCardDescriptorBuilder(FlashCard flashcard) {
        descriptor = new EditFlashCardDescriptor();
        descriptor.setSubject(flashcard.getSubject());
        descriptor.setContent(flashcard.getContent());
        descriptor.setTags(flashcard.getTags());
    }

    /**
     * Sets the {@code Subject} of the {@code EditFlashCardDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withSubject(String sub) {
        descriptor.setSubject(new Subject(sub));
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code EditFlashCardDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withContent(String cont) {
        descriptor.setContent(new Content(cont));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditFlashCardDescriptor}
     * that we are building.
     */
    public EditFlashCardDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditFlashCardDescriptor build() {
        return descriptor;
    }
}
