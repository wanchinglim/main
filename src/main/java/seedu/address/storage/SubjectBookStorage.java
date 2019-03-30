package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.subject.SubjectBook;

/**
 * Represents a storage for {@link SubjectBook}.
 */
public interface SubjectBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSubjectBookFilePath();

    /**
     * Returns SubjectBook data as a {@link ReadOnlySubjectBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySubjectBook> readSubjectBook() throws DataConversionException, IOException;

    /**
     * @see #getSubjectBookFilePath()
     */
    Optional<ReadOnlySubjectBook> readSubjectBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySubjectBook} to the storage.
     * @param subjectBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSubjectBook(ReadOnlySubjectBook subjectBook) throws IOException;

    /**
     * @see #saveSubjectBook(ReadOnlySubjectBook)
     */
    void saveSubjectBook(ReadOnlySubjectBook subjectBook, Path filePath) throws IOException;

}
