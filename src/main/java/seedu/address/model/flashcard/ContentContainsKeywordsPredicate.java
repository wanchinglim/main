package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code FlashCard}'s {@code Content} matches any of the keywords given.
 */
public class ContentContainsKeywordsPredicate implements Predicate<FlashCard> {
    private final List<String> keywords;

    public ContentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(FlashCard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getContent().content, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContentContainsKeywordsPredicate) other).keywords)); // state check
    }

}
