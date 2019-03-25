package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of FlashBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FlashBookStorage flashBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FlashBookStorage flashBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.flashBookStorage = flashBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ FlashBook methods ==============================

    @Override
    public Path getFlashBookFilePath() {
        return flashBookStorage.getFlashBookFilePath();
    }

    @Override
    public Optional<ReadOnlyFlashBook> readFlashBook() throws DataConversionException, IOException {
        return readFlashBook(flashBookStorage.getFlashBookFilePath());
    }

    @Override
    public Optional<ReadOnlyFlashBook> readFlashBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return flashBookStorage.readFlashBook(filePath);
    }

    @Override
    public void saveFlashBook(ReadOnlyFlashBook flashBook) throws IOException {
        saveFlashBook(flashBook, flashBookStorage.getFlashBookFilePath());
    }

    @Override
    public void saveFlashBook(ReadOnlyFlashBook flashBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        flashBookStorage.saveFlashBook(flashBook, filePath);
    }

}
