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
import seedu.address.model.tag.Subject;

/**
 * Contains utility methods for populating {@code FlashBook} with sample data.
 */
public class SampleDataUtil {
    public static final Deadline EMPTY_DEADLINE = new Deadline("");
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new Topic("Math"), new Difficulty("87438807"),
                new Content("Blk 30 Geylang Street 29, #06-40"), EMPTY_DEADLINE,
                getTagSet("friends")),
            new Flashcard(new Topic("Science"), new Difficulty("99272758"),
                new Content("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_DEADLINE,
                getTagSet("colleagues", "friends")),
            new Flashcard(new Topic("Chinese"), new Difficulty("93210283"),
                new Content("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_DEADLINE,
                getTagSet("neighbours")),
            new Flashcard(new Topic("Geography"), new Difficulty("91031282"),
                new Content("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_DEADLINE,
                getTagSet("family")),
            new Flashcard(new Topic("Chemistry"), new Difficulty("92492021"),
                new Content("Blk 47 Tampines Street 20, #17-35"), EMPTY_DEADLINE,
                getTagSet("classmates")),
            new Flashcard(new Topic("Physics"), new Difficulty("92624417"),
                new Content("Blk 45 Aljunied Street 85, #11-31"), EMPTY_DEADLINE,
                getTagSet("colleagues"))
        };
    }

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
    public static Set<Subject> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }

}
