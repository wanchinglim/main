package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FlashBook;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.SubjectTag;

/**
 * Contains utility methods for populating {@code FlashBook} with sample data.
 */
public class SampleDataUtil {
    public static final Deadline EMPTY_DEADLINE = new Deadline("");
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new Topic("Pythagoras Theorem"), new Difficulty("123"),
                new Content("a^2 + b^2 = c^2"), EMPTY_DEADLINE,
                getTagSet("math")),
            new Flashcard(new Topic("Photosynthesis"), new Difficulty("123"),
                new Content("Happens in plants"), EMPTY_DEADLINE,
                getTagSet("science"))
        };
    }

    /* public static SubjectBook[] getSampleSubjects() {
        return new SubjectBook[] {
                new SubjectBook(new SubjectTag("math")),
                new SubjectBook(new SubjectTag("science")),
                new SubjectBook(new SubjectTag("english"))
        };
    }

    public static ReadOnlySubjectBook getSampleSubjectBook() {
        SubjectBook sampleSB = new SubjectBook();
        for (SubjectBook sampleSubject : getSampleSubjects()) {
            sampleSB.addSubject(sampleSubject);
        }
        return sampleSB;
    }*/

    public static ReadOnlyFlashBook getSampleFlashBook() {
        FlashBook sampleAb = new FlashBook();
        for (Flashcard sampleFlashcard : getSampleFlashcards()) {
            sampleAb.addFlashcard(sampleFlashcard);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<SubjectTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(SubjectTag::new)
                .collect(Collectors.toSet());
    }

}
