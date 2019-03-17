package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSubjects.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.SubjectBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newSubject_success() {
        Subject validSubject = new SubjectBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addSubject(validSubject);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validSubject), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validSubject), expectedModel);
    }

    @Test
    public void execute_duplicateSubject_throwsCommandException() {
        Subject subjectInList = model.getAddressBook().getSubjectList().get(0);
        assertCommandFailure(new AddCommand(subjectInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_SUBJECT);
    }

}
