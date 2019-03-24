package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FlashBook;
import seedu.address.model.ReadOnlyFlashBook;

/**
 * Represents a storage for {@link FlashBook}.
 */
public interface FlashBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFlashBookFilePath();

    /**
     * Returns FlashBook data as a {@link ReadOnlyFlashBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFlashBook> readFlashBook() throws DataConversionException, IOException;

    /**
     * @see #getFlashBookFilePath()
     */
    Optional<ReadOnlyFlashBook> readFlashBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFlashBook} to the storage.
     * @param flashBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFlashBook(ReadOnlyFlashBook flashBook) throws IOException;

    /**
     * @see #saveFlashBook(ReadOnlyFlashBook)
     */
    void saveFlashBook(ReadOnlyFlashBook flashBook, Path filePath) throws IOException;

}
