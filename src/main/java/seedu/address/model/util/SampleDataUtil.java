package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static FlashCard[] getSampleFlashCards() {
        return new FlashCard[] {
            new FlashCard(new Subject("English"), new Content("The quick brown fox jumps over the lazy dog."),
                getTagSet("english")),
            new FlashCard(new Subject("Chinese"), new Content("Ni hao ma?"),
                getTagSet("chinese")),
            new FlashCard(new Subject("Malay"), new Content("Aku tak tahu"),
                getTagSet("malay")),
            new FlashCard(new Subject("Literature"), new Content("Where art thou."),
                getTagSet("literature")),
            new FlashCard(new Subject("Social Studies"), new Content("Singapore gained independence in 1965."),
                getTagSet("ss")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (FlashCard sampleFlashCard : getSampleFlashCards()) {
            sampleAb.addFlashCard(sampleFlashCard);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
