package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.FlashBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.subject.ReadOnlySubjectBook;

/**
 * API of the Storage component
 */
public interface Storage extends SubjectBookStorage, FlashBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFlashBookFilePath();

    @Override
    Optional<ReadOnlyFlashBook> readFlashBook() throws DataConversionException, IOException;

    @Override
    void saveFlashBook(ReadOnlyFlashBook flashBook) throws IOException;

    @Override
    Optional<ReadOnlySubjectBook> readSubjectBook() throws DataConversionException, IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleFlashBookChangedEvent(FlashBookChangedEvent event);

}
