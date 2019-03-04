package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

//import seedu.address.testutil.FlashCardBuilder;

public class ContentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContentContainsKeywordsPredicate firstPredicate =
                new ContentContainsKeywordsPredicate(firstPredicateKeywordList);
        ContentContainsKeywordsPredicate secondPredicate =
                new ContentContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContentContainsKeywordsPredicate firstPredicateCopy =
                new ContentContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
    /**
    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ContentContainsKeywordsPredicate predicate =
                new ContentContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new FlashCardBuilder().withSubject("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new FlashCardBuilder().withSubject("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContentContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new FlashCardBuilder().withSubject("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContentContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new FlashCardBuilder().withSubject("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContentContainsKeywordsPredicate predicate = new ContentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FlashCardBuilder().withSubject("Alice").build()));

        // Non-matching keyword
        predicate = new ContentContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new FlashCardBuilder().withSubject("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ContentContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new FlashCardBuilder().withSubject("Alice").withContent("12345").build()));
    }**/
}
