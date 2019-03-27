package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

/**
 * Adds a flashcard to the flash book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static boolean EXISTS = false;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a flashcard to the flash book. "
            + "Parameters: "
            + PREFIX_TOPIC + "TOPIC "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_CONTENT + "CONTENT "
            + "[" + PREFIX_SUBJECT + "SUBJECT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TOPIC + "pythagoras theorem "
            + PREFIX_DIFFICULTY + "123 "
            + PREFIX_CONTENT + "a^2 + b^2 = c^2 "
            + PREFIX_SUBJECT + "math";

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the flash book";

    private final Flashcard toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Flashcard}
     */
    public AddCommand(Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

//        if(!model.getSubjectBook().hasSubject(toAdd.getSubject())
//            model.addSubjectBook(subject);

//        if (model.getSubjectBook().hasSubject(toAdd.getSubject()) ) {
//            model.addSubject(new SubjectBook(toAdd.getSubject()));
//        }

        for (SubjectTag s : model.getSubjectBook()) {
            if (s.toString().equalsIgnoreCase(toAdd.getSubject().toString())) {
                EXISTS = true;
            }
        }

        if (!EXISTS) {
            model.addSubject(toAdd.getSubject());
        }

        model.addFlashcard(toAdd);
        model.commitFlashBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
