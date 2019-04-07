package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.TopicContainsSubjectPredicate;
import seedu.address.model.tag.SubjectTag;

/**
 * Selects a flashcard identified using it's displayed index from the flash book.
 */
public class SelectSubjectCommand extends Command {

    public static final String COMMAND_WORD = "selectSubject";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects all flashcards with identified subject.\n"
            + "Parameters: Subject (must be already in subject list panel)\n"
            + "Example: " + COMMAND_WORD + " science";

    public static final String MESSAGE_SELECT_SUBJECT_SUCCESS = "Selected Subject: %1$s";

    private static SubjectTag targetSubject;

    private static SubjectTag selectSubject = null;

    private static TopicContainsSubjectPredicate predicate;

    private String[] keywords;



    public SelectSubjectCommand(SubjectTag targetSubject, String[] keywords) {
        this.targetSubject = targetSubject;
        this.keywords = keywords;
        selectSubject = targetSubject;
    }

    public static TopicContainsSubjectPredicate getPredicate() {
        return predicate;
    }

    public static void listCommandCalled() {
        selectSubject = null;
    }

    public static SubjectTag getSelectSubject() {
        return selectSubject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<SubjectTag> filteredSubjectBook = model.getFilteredSubjectList();
        predicate = new TopicContainsSubjectPredicate(Arrays.asList(keywords));

        if (!filteredSubjectBook.contains(targetSubject)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBJECT);
        }
        model.setSelectedSubject(targetSubject);
        model.updateFilteredFlashcardList(predicate);

        return new CommandResult(String.format(MESSAGE_SELECT_SUBJECT_SUCCESS, targetSubject));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectSubjectCommand // instanceof handles nulls
                && targetSubject.equals(((SelectSubjectCommand) other).targetSubject)); // state check
    }
}
