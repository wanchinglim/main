package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalFlashcards.ALICE;
import static seedu.address.testutil.TypicalFlashcards.HOON;
import static seedu.address.testutil.TypicalFlashcards.IDA;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FlashBook;
import seedu.address.model.ReadOnlyFlashBook;

public class JsonFlashBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFlashBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readFlashBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readFlashBook(null);
    }

    private java.util.Optional<ReadOnlyFlashBook> readFlashBook(String filePath) throws Exception {
        return new JsonFlashBookStorage(Paths.get(filePath)).readFlashBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFlashBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readFlashBook("notJsonFormatFlashBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readFlashBook_invalidFlashcardFlashBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readFlashBook("invalidFlashcardFlashBook.json");
    }

    @Test
    public void readFlashBook_invalidAndValidFlashcardFlashBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readFlashBook("invalidAndValidFlashcardFlashBook.json");
    }

    @Test
    public void readAndSaveFlashBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.json");
        FlashBook original = getTypicalFlashBook();
        JsonFlashBookStorage jsonFlashBookStorage = new JsonFlashBookStorage(filePath);

        // Save in new file and read back
        jsonFlashBookStorage.saveFlashBook(original, filePath);
        ReadOnlyFlashBook readBack = jsonFlashBookStorage.readFlashBook(filePath).get();
        assertEquals(original, new FlashBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFlashcard(HOON);
        original.removeFlashcard(ALICE);
        jsonFlashBookStorage.saveFlashBook(original, filePath);
        readBack = jsonFlashBookStorage.readFlashBook(filePath).get();
        assertEquals(original, new FlashBook(readBack));

        // Save and read without specifying file path
        original.addFlashcard(IDA);
        jsonFlashBookStorage.saveFlashBook(original); // file path not specified
        readBack = jsonFlashBookStorage.readFlashBook().get(); // file path not specified
        assertEquals(original, new FlashBook(readBack));

    }

    @Test
    public void saveFlashBook_nullFlashBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFlashBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code flashBook} at the specified {@code filePath}.
     */
    private void saveFlashBook(ReadOnlyFlashBook flashBook, String filePath) {
        try {
            new JsonFlashBookStorage(Paths.get(filePath))
                    .saveFlashBook(flashBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFlashBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFlashBook(new FlashBook(), null);
    }
}
