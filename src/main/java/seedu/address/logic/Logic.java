package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    // =============== SUBJECTS ===============

    /**
     * Returns the SubjectBook.
     *
     * @see seedu.address.model.Model#getFlashBook()
     */
    ReadOnlySubjectBook getSubjectBook();

    /** Returns an unmodifiable view of the filtered list of flashcards */
    ObservableList<SubjectTag> getFilteredSubjectList();

    /**
     * Selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     *
     * @see seedu.address.model.Model#selectedFlashcardProperty()
     */
    ReadOnlyProperty<SubjectTag> selectedSubjectProperty();

    /**
     * Sets the selected flashcard in the filtered flashcard list.
     *
     * @see seedu.address.model.Model#setSelectedSubject(SubjectTag)
     */
    void setSelectedSubject(SubjectTag subject);


    // =============== FLASHCARDS ===============

    /**
     * Returns the FlashCards.
     *
     * @see seedu.address.model.Model#getFlashBook()
     */
    ReadOnlyFlashBook getFlashBook();

    /** Returns an unmodifiable view of the filtered list of flashcards */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' flash book file path.
     */
    Path getFlashBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     *
     * @see seedu.address.model.Model#selectedFlashcardProperty()
     */
    ReadOnlyProperty<Flashcard> selectedFlashcardProperty();

    /**
     * Sets the selected flashcard in the filtered flashcard list.
     *
     * @see seedu.address.model.Model#setSelectedFlashcard(Flashcard)
     */
    void setSelectedFlashcard (Flashcard flashcard);

}
