package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUBJECTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.subject.Deadline;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.exceptions.DuplicatePersonException;
import seedu.address.model.subject.exceptions.PersonNotFoundException;

/**
 * Adds/Changes the deadline or exam of an existing subject in the address book/flashcards.
 */

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    public static final String COMMAND_ALIAS = "exam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deadline/exam of a subject identified "
            + "by the index number used in the last subject listing."
            + "Existing deadline will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "[DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "04 April 2028";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline to Subject: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed deadline from Subject: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This deadline clashes with another in the address book.";

    private final Index index;
    private final Deadline deadline;

    /**
     * @param index of the subject in the filtered subject list to edit the deadline
     * @param deadline of the subject to be updated to
     */

    public DeadlineCommand(Index index, Deadline deadline) {
        //requireAllNonNull(index, deadline);
        requireNonNull(index);
        requireNonNull(deadline);

        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        List<Subject> lastShownList = model.getFilteredSubjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Subject subjectToEdit = lastShownList.get(index.getZeroBased());
        Subject editedSubject = new Subject(subjectToEdit.getName(), subjectToEdit.getPhone(), subjectToEdit.getEmail(),
                subjectToEdit.getAddress(), deadline, subjectToEdit.getTags());

        try {
            model.setSubject(subjectToEdit, editedSubject);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target subject cannot be missing");
        }

        //model.updatePerson(subjectToEdit, editedSubject);
        model.updateFilteredSubjectList(PREDICATE_SHOW_ALL_SUBJECTS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedSubject));
    }

    /**
     * Generates a command execution success message based on whether the deadline is added to or removed from
     * {@code subjectToEdit}.
     */

    private String generateSuccessMessage(Subject subjectToEdit) {
        String message = !deadline.value.isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, subjectToEdit);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DeadlineCommand)) {
            return false;
        }

        //state check
        DeadlineCommand e = (DeadlineCommand) other;
        return index.equals(e.index)
                && deadline.equals(e.deadline);
    }
}
