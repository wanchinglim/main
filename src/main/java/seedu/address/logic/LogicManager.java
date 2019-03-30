package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.FlashBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.tag.SubjectTag;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final FlashBookParser flashBookParser;
    private boolean flashBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        flashBookParser = new FlashBookParser();

        // Set flashBookModified to true whenever the models' flash book is modified.
        model.getFlashBook().addListener(observable -> flashBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        flashBookModified = false;

        CommandResult commandResult;
        try {
            Command command = flashBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (flashBookModified) {
            logger.info("Flash book modified, saving to file.");
            try {
                storage.saveFlashBook(model.getFlashBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    // =============== SUBJECTS ===============
    public ReadOnlySubjectBook getSubjectBook() {
        return model.getSubjectBook();
    }

    @Override
    public ObservableList<SubjectTag> getFilteredSubjectList() {
        return model.getFilteredSubjectList();
    }

    @Override
    public ReadOnlyProperty<SubjectTag> selectedSubjectProperty() {
        return model.selectedSubjectProperty();
    }

    @Override
    public void setSelectedSubject(SubjectTag subjectBook) {
        model.setSelectedSubject(subjectBook);
    }



    // =============== FLASHCARDS ===============
    @Override
    public ReadOnlyFlashBook getFlashBook() {
        return model.getFlashBook();
    }

    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return model.getFilteredFlashcardList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getFlashBookFilePath() {
        return model.getFlashBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Flashcard> selectedFlashcardProperty() {
        return model.selectedFlashcardProperty();
    }

    @Override
    public void setSelectedFlashcard(Flashcard flashcard) {
        model.setSelectedFlashcard(flashcard);
    }
}
