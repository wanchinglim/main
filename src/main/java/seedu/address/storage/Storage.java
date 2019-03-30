package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
}
