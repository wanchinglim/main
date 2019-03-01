package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String MESSAGE_SUCCESS = "Listed all tags.";

    public ListTagsCommand() { }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
