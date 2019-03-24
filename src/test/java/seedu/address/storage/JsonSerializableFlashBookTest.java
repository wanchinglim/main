package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.FlashBook;
import seedu.address.testutil.TypicalFlashcards;

public class JsonSerializableFlashBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFlashBookTest");
    private static final Path TYPICAL_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("typicalFlashcardsFlashBook.json");
    private static final Path INVALID_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("invalidFlashcardFlashBook.json");
    private static final Path DUPLICATE_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("duplicateFlashcardFlashBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalFlashcardsFile_success() throws Exception {
        JsonSerializableFlashBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARD_FILE,
                JsonSerializableFlashBook.class).get();
        FlashBook flashBookFromFile = dataFromFile.toModelType();
        FlashBook typicalFlashcardsFlashBook = TypicalFlashcards.getTypicalFlashBook();
        assertEquals(flashBookFromFile, typicalFlashcardsFlashBook);
    }

    @Test
    public void toModelType_invalidFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashBook dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
                JsonSerializableFlashBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateFlashcards_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
                JsonSerializableFlashBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableFlashBook.MESSAGE_DUPLICATE_FLASHCARD);
        dataFromFile.toModelType();
    }

}
