package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.SubjectTag;


/**
 * Edits the details of an existing flashcard in the flash book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the flashcard identified "
            + "by the index number used in the displayed flashcard list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TOPIC + "TOPIC] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "[" + PREFIX_SUBJECT + "SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TOPIC + "NEW TOPIC NAME ";

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited Flashcard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the content book.";

    private final Index index;
    private final EditFlashcardDescriptor editFlashcardDescriptor;

    /**
     * @param index of the flashcard in the filtered flashcard list to edit
     * @param editFlashcardDescriptor details to edit the flashcard with
     */
    public EditCommand(Index index, EditFlashcardDescriptor editFlashcardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashcardDescriptor);

        this.index = index;
        this.editFlashcardDescriptor = new EditFlashcardDescriptor(editFlashcardDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        ObservableList<Flashcard> updatedFlashcardList = model.getUpdatedFlashcardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToEdit = updatedFlashcardList.get(index.getZeroBased());
        Flashcard editedFlashcard = createEditedFlashcard(flashcardToEdit, editFlashcardDescriptor);

        if (!flashcardToEdit.isSameFlashcard(editedFlashcard) && model.hasFlashcard(editedFlashcard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.setFlashcard(flashcardToEdit, editedFlashcard);
        model.commitFlashBook();
        model.setSelectedSubject(editedFlashcard.getSubject());
        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard));
    }

    /**
     * Creates and returns a {@code Flashcard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashcardDescriptor}.
     */
    private static Flashcard createEditedFlashcard(Flashcard flashcardToEdit,
                                                   EditFlashcardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;

        Topic updatedTopic = editFlashcardDescriptor.getTopic().orElse(flashcardToEdit.getTopic());
        Difficulty updatedDifficulty = editFlashcardDescriptor.getDifficulty().orElse(flashcardToEdit.getDifficulty());
        Content updatedContent = editFlashcardDescriptor.getContent().orElse(flashcardToEdit.getContent());
        Deadline updatedDeadline = flashcardToEdit.getDeadline(); //edit command does not allow editing deadlines
        Set<SubjectTag> updatedTags = editFlashcardDescriptor.getTags().orElse(flashcardToEdit.getTags());

        return new Flashcard(updatedTopic, updatedDifficulty, updatedContent, updatedDeadline, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editFlashcardDescriptor.equals(e.editFlashcardDescriptor);
    }

    /**
     * Stores the details to edit the flashcard with. Each non-empty field value will replace the
     * corresponding field value of the flashcard.
     */
    public static class EditFlashcardDescriptor {

        private Topic topic;
        private Difficulty difficulty;
        private Deadline deadline;
        private Content content;
        private Set<SubjectTag> subjectTag;
        private SubjectTag subject;
        private String subjectName;

        public EditFlashcardDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFlashcardDescriptor(EditFlashcardDescriptor toCopy) {
            setTopic(toCopy.topic);
            setDifficulty(toCopy.difficulty);
            setDeadline(toCopy.deadline);
            setContent(toCopy.content);
            setTags(toCopy.subjectTag);
            /*if (!toCopy.subjectTag.isEmpty()) {
                for (SubjectTag s : toCopy.subjectTag) {
                    setSubject(s);
                    setSubjectName(s);
                    break;
                }
            }*/
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(topic, difficulty, content, subjectTag);
        }

        public void setTopic(Topic topic) {
            this.topic = topic;
        }

        public Optional<Topic> getTopic() {
            return Optional.ofNullable(topic);
        }

        public void setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        public Optional<Difficulty> getDifficulty() {
            return Optional.ofNullable(difficulty);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(content);
        }

        public void setSubject(SubjectTag subject) {
            this.subject = subject;
        }

        public Optional<SubjectTag> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setSubjectName(SubjectTag subjectTag) {
            this.subjectName = subjectTag.toString();
        }

        public Optional<String> getSubjectName() {
            return Optional.ofNullable(subjectName);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<SubjectTag> tags) {
            this.subjectTag = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<SubjectTag>> getTags() {
            return (subjectTag != null) ? Optional.of(Collections.unmodifiableSet(subjectTag)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFlashcardDescriptor)) {
                return false;
            }

            // state check
            EditFlashcardDescriptor e = (EditFlashcardDescriptor) other;

            return getTopic().equals(e.getTopic())
                    && getDifficulty().equals(e.getDifficulty())
                    && getContent().equals(e.getContent())
                    && getTags().equals(e.getTags());
        }
    }
}
