package seedu.address.logic.commands;

//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import org.junit.Before;
//import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.flashcard.FlashCard;
//import seedu.address.testutil.FlashCardBuilder;

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

    /**
    @Test
    public void execute_newFlashCard_success() {
        FlashCard validFlashCard = new FlashCardBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFlashCard(validFlashCard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validFlashCard), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validFlashCard), expectedModel);
    }

    @Test
    public void execute_duplicateFlashCard_throwsCommandException() {
        FlashCard flashcardInList = model.getAddressBook().getFlashCardList().get(0);
        assertCommandFailure(new AddCommand(flashcardInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }**/

}
