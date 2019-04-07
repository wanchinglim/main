package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Flashcard}'s {@code Topic} matches any of the keywords given.
 */
public class TopicContainsDoublePredicate implements Predicate<Flashcard> {
    private final List<String> keywordsSubject;
    private final List<String> keywordsDifficulty;

    public TopicContainsDoublePredicate(List<String> keywordsSubject, List<String> keywordsDifficulty) {
        this.keywordsSubject = keywordsSubject;
        this.keywordsDifficulty = keywordsDifficulty;

    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywordsSubject.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                flashcard.getSubject().subjectName, keyword))
                && keywordsDifficulty.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        flashcard.getDifficulty().value, keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopicContainsDoublePredicate // instanceof handles nulls
                && keywordsDifficulty.equals(((TopicContainsDoublePredicate) other).keywordsDifficulty)); // state check
    }

}
