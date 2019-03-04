package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Subject;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing flashcard in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the flashcard identified "
            + "by the index number used in the displayed flashcard list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBJECT + "English "
            + PREFIX_CONTENT + "The quick brown fox jumps over the lazy dog. ";

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited FlashCard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the address book.";

    private final Index index;
    private final EditFlashCardDescriptor editFlashCardDescriptor;

    /**
     * @param index of the flashcard in the filtered flashcard list to edit
     * @param editFlashCardDescriptor details to edit the flashcard with
     */
    public EditCommand(Index index, EditFlashCardDescriptor editFlashCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashCardDescriptor);

        this.index = index;
        this.editFlashCardDescriptor = new EditFlashCardDescriptor(editFlashCardDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<FlashCard> lastShownList = model.getFilteredFlashCardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        FlashCard flashcardToEdit = lastShownList.get(index.getZeroBased());
        FlashCard editedFlashCard = createEditedFlashCard(flashcardToEdit, editFlashCardDescriptor);

        if (!flashcardToEdit.isSameFlashCard(editedFlashCard) && model.hasFlashCard(editedFlashCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.setFlashCard(flashcardToEdit, editedFlashCard);
        model.updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard));
    }

    /**
     * Creates and returns a {@code FlashCard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashCardDescriptor}.
     */
    private static FlashCard createEditedFlashCard(FlashCard flashcardToEdit,
        EditFlashCardDescriptor editFlashCardDescriptor) {
        assert flashcardToEdit != null;

        Subject updatedSubject = editFlashCardDescriptor.getSubject().orElse(flashcardToEdit.getSubject());
        Content updatedContent = editFlashCardDescriptor.getContent().orElse(flashcardToEdit.getContent());
        Set<Tag> updatedTags = editFlashCardDescriptor.getTags().orElse(flashcardToEdit.getTags());

        return new FlashCard(updatedSubject, updatedContent, updatedTags);
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
                && editFlashCardDescriptor.equals(e.editFlashCardDescriptor);
    }

    /**
     * Stores the details to edit the flashcard with. Each non-empty field value will replace the
     * corresponding field value of the flashcard.
     */
    public static class EditFlashCardDescriptor {
        private Subject name;
        private Content phone;
        private Set<Tag> tags;

        public EditFlashCardDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFlashCardDescriptor(EditFlashCardDescriptor toCopy) {
            setSubject(toCopy.name);
            setContent(toCopy.phone);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, tags);
        }

        public void setSubject(Subject name) {
            this.name = name;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(name);
        }

        public void setContent(Content phone) {
            this.phone = phone;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(phone);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFlashCardDescriptor)) {
                return false;
            }

            // state check
            EditFlashCardDescriptor e = (EditFlashCardDescriptor) other;

            return getSubject().equals(e.getSubject())
                    && getContent().equals(e.getContent())
                    && getTags().equals(e.getTags());
        }
    }
}
