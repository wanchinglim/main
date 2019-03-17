package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.subject.Subject;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withSubject("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Subject} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSubject(Subject subject) {
        addressBook.addSubject(subject);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
