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

/**
 * A class to access FlashBook data stored as a json file on the hard disk.
 */
public class JsonFlashBookStorage implements FlashBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFlashBookStorage.class);

    private Path filePath;

    public JsonFlashBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFlashBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFlashBook> readFlashBook() throws DataConversionException {
        return readFlashBook(filePath);
    }

    /**
     * Similar to {@link #readFlashBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFlashBook> readFlashBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFlashBook> jsonFlashBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableFlashBook.class);
        if (!jsonFlashBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFlashBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFlashBook(ReadOnlyFlashBook flashBook) throws IOException {
        saveFlashBook(flashBook, filePath);
    }

    /**
     * Similar to {@link #saveFlashBook(ReadOnlyFlashBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFlashBook(ReadOnlyFlashBook flashBook, Path filePath) throws IOException {
        requireNonNull(flashBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashBook(flashBook), filePath);
    }

}
