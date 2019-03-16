package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditSubjectDescriptor;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Subject.
 */
public class SubjectUtil {

    /**
     * Returns an add command string for adding the {@code subject}.
     */
    public static String getAddCommand(Subject subject) {
        return AddCommand.COMMAND_WORD + " " + getSubjectDetails(subject);
    }

    /**
     * Returns the part of command string for the given {@code subject}'s details.
     */
    public static String getSubjectDetails(Subject subject) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + subject.getName().fullName + " ");
        sb.append(PREFIX_PHONE + subject.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + subject.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + subject.getAddress().value + " ");
        subject.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSubjectDescriptor}'s details.
     */
    public static String getEditSubjectDescriptorDetails(EditSubjectDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
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