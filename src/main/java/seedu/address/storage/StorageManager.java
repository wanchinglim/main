package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.FlashBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.subject.ReadOnlySubjectBook;

/**
 * Manages storage of FlashBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FlashBookStorage flashBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private SubjectBookStorage subjectBookStorage;


    public StorageManager(FlashBookStorage flashBookStorage,
                            UserPrefsStorage userPrefsStorage) {
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

    // ================ SubjectBook methods ==============================
    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getSubjectBookFilePath() {
        return subjectBookStorage.getSubjectBookFilePath();
    }

    @Override
    public Optional<ReadOnlySubjectBook> readSubjectBook() throws DataConversionException, IOException {
        return readSubjectBook(subjectBookStorage.getSubjectBookFilePath());
    }

    /**
     * @param filePath
     * @see #getSubjectBookFilePath()
     */
    @Override
    public Optional<ReadOnlySubjectBook> readSubjectBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return subjectBookStorage.readSubjectBook(filePath);
    }

    /**
     * Saves the given {@link ReadOnlySubjectBook} to the storage.
     *
     * @param subjectBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveSubjectBook(ReadOnlySubjectBook subjectBook) throws IOException {
        saveSubjectBook(subjectBook, subjectBookStorage.getSubjectBookFilePath());
    }

    /**
     * @param subjectBook
     * @param filePath
     * @see #saveSubjectBook(ReadOnlySubjectBook)
     */
    @Override
    public void saveSubjectBook(ReadOnlySubjectBook subjectBook, Path filePath) throws IOException {

    }

    @Override
    @Subscribe
    public void handleFlashBookChangedEvent(FlashBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveFlashBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


}
