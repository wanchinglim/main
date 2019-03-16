package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalSubjects;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_SUBJECTS_FILE = TEST_DATA_FOLDER.resolve("typicalSubjectsAddressBook.json");
    private static final Path INVALID_SUBJECT_FILE = TEST_DATA_FOLDER.resolve("invalidSubjectAddressBook.json");
    private static final Path DUPLICATE_SUBJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateSubjectAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalSubjectsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_SUBJECTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalSubjectsAddressBook = TypicalSubjects.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalSubjectsAddressBook);
    }

    @Test
    public void toModelType_invalidSubjectFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_SUBJECT_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateSubjects_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SUBJECT_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableAddressBook.MESSAGE_DUPLICATE_SUBJECT);
        dataFromFile.toModelType();
    }

}
