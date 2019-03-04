package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.flashcard.FlashCard;

/**
 * A utility class containing a list of {@code FlashCard} objects to be used in tests.
 */
public class TypicalFlashCards {

    public static final FlashCard MALAY = new FlashCardBuilder().withSubject("Malay")
        .withContent("Aku tak tahu").withTags("malay").build();
    public static final FlashCard CHEMISTRY = new FlashCardBuilder().withSubject("Chemistry")
        .withContent("H20").withTags("chemistry").build();
    public static final FlashCard PHYSICS = new FlashCardBuilder().withSubject("Physics").withContent("Force")
        .withTags("physics").build();
    public static final FlashCard COMP_STUDIES = new FlashCardBuilder().withSubject("Computer Studies")
        .withContent("Learn java").withTags("cs").build();
    public static final FlashCard LITERATURE = new FlashCardBuilder().withSubject("Literature")
        .withContent("9482224").withTags("lit").build();

    // Manually added
    public static final FlashCard PSYCH = new FlashCardBuilder().withSubject("Psychology").withContent("ABC")
        .withTags("psych").build();
    public static final FlashCard ACCOUNTING = new FlashCardBuilder().withSubject("Principals of Accounting")
        .withContent("Debit credit").withTags("accounting").build();

    // Manually added - FlashCard's details found in {@code CommandTestUtil}
    public static final FlashCard ENGLISH = new FlashCardBuilder().withSubject(VALID_SUBJECT_ENGLISH)
        .withContent(VALID_CONTENT_ENGLISH).withTags(VALID_TAG_ENGLISH).build();
    public static final FlashCard CHINESE = new FlashCardBuilder().withSubject(VALID_SUBJECT_CHINESE)
        .withContent(VALID_CONTENT_CHINESE).withTags(VALID_TAG_CHINESE).build();

    public static final String KEYWORD_MATCHING_PRINCIPLES = "Principles"; // A keyword that matches MEIER

    private TypicalFlashCards() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical flashcards.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (FlashCard flashcard : getTypicalFlashCards()) {
            ab.addFlashCard(flashcard);
        }
        return ab;
    }

    public static List<FlashCard> getTypicalFlashCards() {
        return new ArrayList<>(Arrays.asList(MALAY, CHEMISTRY, PHYSICS, COMP_STUDIES, LITERATURE));
    }
}
