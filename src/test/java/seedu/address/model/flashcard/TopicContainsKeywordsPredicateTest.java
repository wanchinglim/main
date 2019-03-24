package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.FlashcardBuilder;

public class TopicContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TopicContainsKeywordsPredicate firstPredicate = new TopicContainsKeywordsPredicate(firstPredicateKeywordList);
        TopicContainsKeywordsPredicate secondPredicate = new TopicContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TopicContainsKeywordsPredicate firstPredicateCopy =
                new TopicContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different flashcard -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_topicContainsKeywords_returnsTrue() {
        // One keyword
        TopicContainsKeywordsPredicate predicate =
                new TopicContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new FlashcardBuilder().withTopic("Alice Bob").build()));

        // Multiple keywords
        predicate = new TopicContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new FlashcardBuilder().withTopic("Alice Bob").build()));

        // Only one matching keyword
        predicate = new TopicContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new FlashcardBuilder().withTopic("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new TopicContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new FlashcardBuilder().withTopic("Alice Bob").build()));
    }

    @Test
    public void test_topicDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TopicContainsKeywordsPredicate predicate = new TopicContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FlashcardBuilder().withTopic("Alice").build()));

        // Non-matching keyword
        predicate = new TopicContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new FlashcardBuilder().withTopic("Alice Bob").build()));

        // Keywords match difficulty, email and content, but does not match topic
        predicate = new TopicContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new FlashcardBuilder().withTopic("Alice").withDifficulty("12345")
                .withEmail("alice@email.com").withContent("Main Street").build()));
    }
}
