package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.SubjectTag;

/**
 * Represents a Flashcard in the flash book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Flashcard {

    // Identity fields
    private final Topic topic;
    private final Difficulty difficulty;

    // Data fields
    private final Content content;
    private final Deadline deadline;
    private final Set<SubjectTag> subjectTag = new HashSet<>();

    private SubjectTag subject;

    private String subjectName;

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Topic topic, Difficulty difficulty, Content content,
                     Deadline deadline, Set<SubjectTag> subjectTag) {
        requireAllNonNull(topic, difficulty, content, subjectTag);
        this.topic = topic;
        this.difficulty = difficulty;
        this.content = content;
        this.deadline = deadline;
        this.subjectTag.addAll(subjectTag);
        for (SubjectTag s : subjectTag) {
            this.subject = s;
            break;
        }
        this.subjectName = subjectTag.toString();
    }

    public Topic getTopic() {
        return topic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Content getContent() {
        return content;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public SubjectTag getSubject() {
        return subject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<SubjectTag> getTags() {
        return Collections.unmodifiableSet(subjectTag);
    }

    /**
     * Returns true if both flashcards of the same topic have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two flashcards.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }

        return otherFlashcard != null
                && otherFlashcard.getTopic().equals(getTopic())
                && otherFlashcard.getContent().equals(getContent());
    }

    /**
     * Returns true if both flashcards have the same identity and data fields.
     * This defines a stronger notion of equality between two flashcards.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Flashcard)) {
            return false;
        }

        Flashcard otherFlashcard = (Flashcard) other;
        return otherFlashcard.getTopic().equals(getTopic())
                && otherFlashcard.getDifficulty().equals(getDifficulty())
                && otherFlashcard.getContent().equals(getContent())
                && otherFlashcard.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(topic, difficulty, content, subjectTag);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTopic())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Content: ")
                .append(getContent())
                .append(" SubjectTag: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
