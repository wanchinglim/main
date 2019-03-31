package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Flashcard}'s {@code Name} matches any of the keywords given.
 */
public class TopicContainsDifficultyPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public TopicContainsDifficultyPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getDifficulty().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopicContainsDifficultyPredicate // instanceof handles nulls
                && keywords.equals(((TopicContainsDifficultyPredicate) other).keywords)); // state check
    }

}
