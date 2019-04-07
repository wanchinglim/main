package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SelectSubjectCommand;
import seedu.address.model.tag.SubjectTag;

/**
 * Panel containing the list of subjects.
 */
public class SubjectListPanel extends UiPart<Region> {

    private static final String FXML = "SubjectListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SubjectListPanel.class);

    @FXML
    private ListView<SubjectTag> subjectListView;

    private ObservableValue<SubjectTag> s;

    public SubjectListPanel(ObservableList<SubjectTag> subjectList, ObservableValue<SubjectTag> selectedSubject,
                              Consumer<SubjectTag> onSelectedSubjectBookChange) {
        super(FXML);

        subjectListView.setItems(subjectList);
        subjectListView.setCellFactory(listView -> new SubjectTagListViewCell());
        subjectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in subject list panel changed to : '" + newValue + "'");
            onSelectedSubjectBookChange.accept(newValue);
        });
        selectedSubject.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected subject changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected flashcard,
            // otherwise we would have an infinite loop.
            if (Objects.equals(subjectListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                subjectListView.getSelectionModel().clearSelection();
            } else {
                int index = subjectListView.getItems().indexOf(newValue);
                subjectListView.scrollTo(index);
                subjectListView.getSelectionModel().clearAndSelect(index);
                new SelectSubjectCommand(newValue, newValue.toString().split("\\s+"));
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code SubjectBook} using a {@code SubjectBookCard}.
     */
    class SubjectTagListViewCell extends ListCell<SubjectTag> {
        @Override
        protected void updateItem(SubjectTag sb, boolean empty) {
            super.updateItem(sb, empty);

            if (empty || sb == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SubjectCard(sb, getIndex() + 1).getRoot());
            }
        }
    }

}
