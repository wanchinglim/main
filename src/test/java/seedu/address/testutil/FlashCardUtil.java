package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.tag.Tag;

/**
 * A utility class for FlashCard.
 */
public class FlashCardUtil {

    /**
     * Returns an add command string for adding the {@code flashcard}.
     */
    public static String getAddCommand(FlashCard flashcard) {
        return AddCommand.COMMAND_WORD + " " + getFlashCardDetails(flashcard);
    }

    /**
     * Returns the part of command string for the given {@code flashcard}'s details.
     */
    public static String getFlashCardDetails(FlashCard flashcard) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_SUBJECT + flashcard.getSubject().subject + " ");
        sb.append(PREFIX_CONTENT + flashcard.getContent().content + " ");
        flashcard.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFlashCardDescriptor}'s details.
     */
    public static String getEditFlashCardDescriptorDetails(EditFlashCardDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getSubject().ifPresent(name -> sb.append(PREFIX_SUBJECT).append(name.subject).append(" "));
        descriptor.getContent().ifPresent(phone -> sb.append(PREFIX_CONTENT).append(phone.content).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
