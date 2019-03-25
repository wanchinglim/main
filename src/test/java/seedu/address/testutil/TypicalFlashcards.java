package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FlashBook;
import seedu.address.model.flashcard.Flashcard;

/**
 * A utility class containing a list of {@code Flashcard} objects to be used in tests.
 */
public class TypicalFlashcards {

    public static final Flashcard ALICE = new FlashcardBuilder().withTopic("Alice Pauline")
            .withContent("123, Jurong West Ave 6, #08-111")
            .withDifficulty("94351253")
            .withTags("friends").build();
    public static final Flashcard BENSON = new FlashcardBuilder().withTopic("Benson Meier")
            .withContent("311, Clementi Ave 2, #02-25")
            .withDifficulty("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Flashcard CARL = new FlashcardBuilder().withTopic("Carl Kurz").withDifficulty("95352563")
            .withContent("wall street").build();
    public static final Flashcard DANIEL = new FlashcardBuilder().withTopic("Daniel Meier").withDifficulty("87652533")
            .withContent("10th street").withTags("friends").build();
    public static final Flashcard ELLE = new FlashcardBuilder().withTopic("Elle Meyer").withDifficulty("9482224")
            .withContent("michegan ave").build();
    public static final Flashcard FIONA = new FlashcardBuilder().withTopic("Fiona Kunz").withDifficulty("9482427")
            .withContent("little tokyo").build();
    public static final Flashcard GEORGE = new FlashcardBuilder().withTopic("George Best").withDifficulty("9482442")
            .withContent("4th street").withDeadline("21 August 2022").build();

    // Manually added
    public static final Flashcard HOON = new FlashcardBuilder().withTopic("Hoon Meier").withDifficulty("8482424")
            .withContent("little india").build();
    public static final Flashcard IDA = new FlashcardBuilder().withTopic("Ida Mueller").withDifficulty("8482131")
            .withContent("chicago ave").build();

    // Manually added - Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard AMY =
            new FlashcardBuilder().withTopic(VALID_TOPIC_AMY).withDifficulty(VALID_DIFFICULTY_AMY)
            .withContent(VALID_CONTENT_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Flashcard BOB =
            new FlashcardBuilder().withTopic(VALID_TOPIC_BOB).withDifficulty(VALID_DIFFICULTY_BOB)
            .withContent(VALID_CONTENT_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFlashcards() {} // prevents instantiation

    /**
     * Returns an {@code FlashBook} with all the typical subjects.
     */
    public static FlashBook getTypicalFlashBook() {
        FlashBook ab = new FlashBook();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            ab.addFlashcard(flashcard);
        }
        return ab;
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
