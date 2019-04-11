package seedu.address.testutil;

import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;


/**
 * A utility class to help with building Flashbook objects.
 * Example usage: <br>
 *     {@code FlashBook ab = new FlashBookBuilder().withFlashcard("John", "Doe").build();}
 */
public class SubjectBookBuilder {

    private SubjectBook subjectBook;

    public SubjectBookBuilder() {
        subjectBook = new SubjectBook();
    }

    public SubjectBookBuilder(SubjectBook subjectBook) {
        this.subjectBook = subjectBook;
    }

    /**
     * Adds a new {@code Flashcard} to the {@code FlashBook} that we are building.
     */
    public SubjectBookBuilder withSubject(SubjectTag subjectTag) {
        subjectBook.addSubject(subjectTag);
        return this;
    }

    public SubjectBook build() {
        return subjectBook;
    }
}
