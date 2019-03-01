package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class FlashCard {

    // Identity fields
    private final Subject subject;
    private final Content content;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public FlashCard(Subject sub, Content cont, Set<Tag> tags) {
        requireAllNonNull(sub, cont, tags);
        this.subject = sub;
        this.content = cont;
        this.tags.addAll(tags);
    }

    public Subject getSubject() {
        return subject;
    }

    public Content getContent() {
        return content;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameFlashCard(FlashCard other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getSubject().equals(getSubject())
                && (other.getContent().equals(getContent()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FlashCard)) {
            return false;
        }

        FlashCard otherFlashCard = (FlashCard) other;
        return otherFlashCard.getSubject().equals(getSubject())
                && otherFlashCard.getContent().equals(getContent())
                && otherFlashCard.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(subject, content, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Subject: ")
                .append(getSubject())
                .append(" Content: ")
                .append(getContent())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
