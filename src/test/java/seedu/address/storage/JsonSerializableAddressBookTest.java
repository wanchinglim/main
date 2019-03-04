package seedu.address.storage;

//import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
//import org.junit.Test;
import org.junit.rules.ExpectedException;

/**import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalFlashCards;**/

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_FLASHCARDS_FILE = TEST_DATA_FOLDER.resolve("typicalFlashCardsAddressBook.json");
    private static final Path INVALID_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("invalidFlashCardAddressBook.json");
    private static final Path DUPLICATE_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("duplicateFlashCardAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
    @Test
    public void toModelType_typicalFlashCardsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARDS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalFlashCardsAddressBook = TypicalFlashCards.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalFlashCardsAddressBook);
    }

    @Test
    public void toModelType_invalidFlashCardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateFlashCards_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableAddressBook.MESSAGE_DUPLICATE_FLASHCARD);
        dataFromFile.toModelType();
    }**/

}
