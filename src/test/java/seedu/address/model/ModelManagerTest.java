package seedu.address.model;

/**import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.TypicalFlashCards.CHEMISTRY;
import static seedu.address.testutil.TypicalFlashCards.CHINESE;
import static seedu.address.testutil.TypicalFlashCards.MALAY;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;**/

import org.junit.Rule;
//import org.junit.Test;
import org.junit.rules.ExpectedException;

/**import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.ContentContainsKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.FlashCardBuilder;**/

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();
    /**
    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedFlashCard());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasFlashCard_nullFlashCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasFlashCard(null);
    }

    @Test
    public void hasFlashCard_flashcardNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasFlashCard(MALAY));
    }

    @Test
    public void hasFlashCard_flashcardInAddressBook_returnsTrue() {
        modelManager.addFlashCard(MALAY);
        assertTrue(modelManager.hasFlashCard(MALAY));
    }

    @Test
    public void deleteFlashCard_flashcardIsSelectedAndFirstFlashCardInFilteredFlashCardList_selectionCleared() {
        modelManager.addFlashCard(MALAY);
        modelManager.setSelectedFlashCard(MALAY);
        modelManager.deleteFlashCard(MALAY);
        assertEquals(null, modelManager.getSelectedFlashCard());
    }

    @Test
    public void deleteFlashCard_flashcardIsSelectedAndSecondFlashCardInFilteredFlashCardList_firstFlashCardSelected() {
        modelManager.addFlashCard(MALAY);
        modelManager.addFlashCard(CHINESE);
        assertEquals(Arrays.asList(MALAY, CHINESE), modelManager.getFilteredFlashCardList());
        modelManager.setSelectedFlashCard(CHINESE);
        modelManager.deleteFlashCard(CHINESE);
        assertEquals(MALAY, modelManager.getSelectedFlashCard());
    }

    @Test
    public void setFlashCard_flashcardIsSelected_selectedFlashCardUpdated() {
        modelManager.addFlashCard(MALAY);
        modelManager.setSelectedFlashCard(MALAY);
        FlashCard updatedAlice = new FlashCardBuilder(MALAY).build();
        modelManager.setFlashCard(MALAY, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedFlashCard());
    }

    @Test
    public void getFilteredFlashCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredFlashCardList().remove(0);
    }

    @Test
    public void setSelectedFlashCard_flashcardNotInFilteredFlashCardList_throwsFlashCardNotFoundException() {
        thrown.expect(FlashCardNotFoundException.class);
        modelManager.setSelectedFlashCard(MALAY);
    }

    @Test
    public void setSelectedFlashCard_flashcardInFilteredFlashCardList_setsSelectedFlashCard() {
        modelManager.addFlashCard(MALAY);
        assertEquals(Collections.singletonList(MALAY), modelManager.getFilteredFlashCardList());
        modelManager.setSelectedFlashCard(MALAY);
        assertEquals(MALAY, modelManager.getSelectedFlashCard());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withFlashCard(MALAY).withFlashCard(CHEMISTRY).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = MALAY.getSubject().subject.split("\\s+");
        modelManager.updateFilteredFlashCardList(new ContentContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }**/
}
