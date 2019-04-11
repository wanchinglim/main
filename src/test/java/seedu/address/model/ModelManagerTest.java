package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.TypicalFlashcards.CHINESE;
import static seedu.address.testutil.TypicalFlashcards.ENGLISH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.TopicContainsKeywordsPredicate;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;
import seedu.address.testutil.FlashBookBuilder;
import seedu.address.testutil.FlashcardBuilder;
import seedu.address.testutil.SubjectBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FlashBook(), new FlashBook(modelManager.getFlashBook()));
        assertEquals(new SubjectBook(), new SubjectBook(modelManager.getSubjectBook()));
        assertEquals(null, modelManager.getSelectedFlashcard());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFlashBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFlashBookFilePath(Paths.get("new/address/book/file/path"));
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
    public void setFlashBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setFlashBookFilePath(null);
    }

    @Test
    public void setFlashBookFilePath_validPath_setsFlashBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setFlashBookFilePath(path);
        assertEquals(path, modelManager.getFlashBookFilePath());
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasFlashcard(null);
    }

    @Test
    public void hasFlashcard_flashcardNotInFlashBook_returnsFalse() {
        assertFalse(modelManager.hasFlashcard(ENGLISH));
    }

    @Test
    public void hasFlashcard_flashcardInFlashBook_returnsTrue() {
        modelManager.addFlashcard(ENGLISH);
        assertTrue(modelManager.hasFlashcard(ENGLISH));
    }

    @Test
    public void deleteFlashcard_flashcardIsSelectedAndFirstFlashcardInFilteredFlashcardList_selectionCleared() {
        modelManager.addFlashcard(ENGLISH);
        modelManager.setSelectedFlashcard(ENGLISH);
        modelManager.deleteFlashcard(ENGLISH);
        assertEquals(null, modelManager.getSelectedFlashcard());
    }

    @Test
    public void deleteFlashcard_flashcardIsSelectedAndSecondFlashcardInFilteredFlashcardList_firstFlashcardSelected() {
        modelManager.addFlashcard(ENGLISH);
        modelManager.addFlashcard(CHINESE);
        assertEquals(Arrays.asList(ENGLISH, CHINESE), modelManager.getFilteredFlashcardList());
        modelManager.setSelectedFlashcard(CHINESE);
        modelManager.deleteFlashcard(CHINESE);
        assertEquals(ENGLISH, modelManager.getSelectedFlashcard());
    }

    @Test
    public void setFlashcard_flashcardIsSelected_selectedFlashcardUpdated() {
        modelManager.addFlashcard(ENGLISH);
        modelManager.setSelectedFlashcard(ENGLISH);
        Flashcard updatedAlice = new FlashcardBuilder(ENGLISH).build();
        modelManager.setFlashcard(ENGLISH, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedFlashcard());
    }

    @Test
    public void getFilteredFlashcardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredFlashcardList().remove(0);
    }

    @Test
    public void setSelectedFlashcard_flashcardNotInFilteredFlashcardList_throwsFlashcardNotFoundException() {
        thrown.expect(FlashcardNotFoundException.class);
        modelManager.setSelectedFlashcard(ENGLISH);
    }

    @Test
    public void setSelectedFlashcard_flashcardInFilteredFlashcardList_setsSelectedFlashcard() {
        modelManager.addFlashcard(ENGLISH);
        assertEquals(Collections.singletonList(ENGLISH), modelManager.getFilteredFlashcardList());
        modelManager.setSelectedFlashcard(ENGLISH);
        assertEquals(ENGLISH, modelManager.getSelectedFlashcard());
    }

    @Test
    public void equals() {
        FlashBook flashBook = new FlashBookBuilder().withFlashcard(ENGLISH).withFlashcard(CHINESE).build();
        SubjectBook subjectBook = new SubjectBookBuilder()
                .withSubject(new SubjectTag("english"))
                .withSubject(new SubjectTag("chinese")).build();
        FlashBook differentFlashBook = new FlashBook();
        UserPrefs userPrefs = new UserPrefs();
        SubjectBook differentSubjectBook = new SubjectBook();

        // same values -> returns true
        modelManager = new ModelManager(differentSubjectBook, flashBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(differentSubjectBook, flashBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different flashBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSubjectBook, differentFlashBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ENGLISH.getTopic().fullTopic.split("\\s+");
        modelManager.updateFilteredFlashcardList(new TopicContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(subjectBook, flashBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFlashBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(subjectBook, flashBook, differentUserPrefs)));
    }
}
