package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SUBJECT;

import seedu.address.logic.commands.SelectSubjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.SubjectTag;

/**
 * Parses input arguments and creates a new SelectSubjectCommand object
 */
public class SelectSubjectCommandParser implements Parser<SelectSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectSubjectCommand
     * and returns an SelectSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectSubjectCommand parse(String args) throws ParseException {
        try {
            SubjectTag subjectTag = ParserUtil.parseSubject(args);

            return new SelectSubjectCommand(subjectTag);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_SUBJECT, SelectSubjectCommand.MESSAGE_USAGE), pe);
        }
    }

}
