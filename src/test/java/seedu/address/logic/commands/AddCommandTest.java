package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.flashcard.FlashCard;
//import seedu.address.testutil.FlashCardBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    /**
    @Test
    public void execute_flashcardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashCardAdded modelStub = new ModelStubAcceptingFlashCardAdded();
        FlashCard validFlashCard = new FlashCardBuilder().build();

        CommandResult commandResult = new AddCommand(validFlashCard).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFlashCard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashCard), modelStub.flashcardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateFlashCard_throwsCommandException() throws Exception {
        FlashCard validFlashCard = new FlashCardBuilder().build();
        AddCommand addCommand = new AddCommand(validFlashCard);
        ModelStub modelStub = new ModelStubWithFlashCard(validFlashCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_FLASHCARD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        FlashCard alice = new FlashCardBuilder().withSubject("Alice").build();
        FlashCard bob = new FlashCardBuilder().withSubject("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashCard(FlashCard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashCard(FlashCard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashCard(FlashCard target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashCard(FlashCard target, FlashCard editedFlashCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FlashCard> getFilteredFlashCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashCardList(Predicate<FlashCard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<FlashCard> selectedFlashCardProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FlashCard getSelectedFlashCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedFlashCard(FlashCard flashcard) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single flashcard.
     */
    private class ModelStubWithFlashCard extends ModelStub {
        private final FlashCard flashcard;

        ModelStubWithFlashCard(FlashCard flashcard) {
            requireNonNull(flashcard);
            this.flashcard = flashcard;
        }

        @Override
        public boolean hasFlashCard(FlashCard flashcard) {
            requireNonNull(flashcard);
            return this.flashcard.isSameFlashCard(flashcard);
        }
    }

    /**
     * A Model stub that always accept the flashcard being added.
     */
    private class ModelStubAcceptingFlashCardAdded extends ModelStub {
        final ArrayList<FlashCard> flashcardsAdded = new ArrayList<>();

        @Override
        public boolean hasFlashCard(FlashCard flashcard) {
            requireNonNull(flashcard);
            return flashcardsAdded.stream().anyMatch(flashcard::isSameFlashCard);
        }

        @Override
        public void addFlashCard(FlashCard flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
