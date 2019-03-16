package seedu.address.model.subject;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Subject}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Subject> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Subject subject) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(subject.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}