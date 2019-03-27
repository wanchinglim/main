package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.subject.ReadOnlySubjectBook;

/**
 * A class to access FlashBook data stored as a json file on the hard disk.
 */
public class JsonSubjectBookStorage implements SubjectBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonFlashBookStorage.class);

    private Path filePath;

    public JsonSubjectBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSubjectBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySubjectBook> readSubjectBook() throws DataConversionException {
        return readSubjectBook(filePath);
    }

    /**
     * Similar to {@link #readSubjectBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySubjectBook> readSubjectBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSubjectBook> jsonSubjectBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableSubjectBook.class);
        if (!jsonSubjectBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSubjectBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSubjectBook(ReadOnlySubjectBook subjectBook) throws IOException {
        saveSubjectBook(subjectBook, filePath);
    }

    /**
     * Similar to {@link #saveSubjectBook(ReadOnlySubjectBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSubjectBook(ReadOnlySubjectBook subjectBook, Path filePath) throws IOException {
        requireNonNull(subjectBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSubjectBook(subjectBook), filePath);
    }
}
