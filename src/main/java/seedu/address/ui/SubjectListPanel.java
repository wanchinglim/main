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
import seedu.address.model.subject.Subject;

/**
 * Panel containing the list of subjects.
 */
public class SubjectListPanel extends UiPart<Region> {
    private static final String FXML = "SubjectListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SubjectListPanel.class);

    @FXML
    private ListView<Subject> subjectListView;

    public SubjectListPanel(ObservableList<Subject> subjectList, ObservableValue<Subject> selectedSubject,
                            Consumer<Subject> onSelectedSubjectChange) {
        super(FXML);
        subjectListView.setItems(subjectList);
        subjectListView.setCellFactory(listView -> new SubjectListViewCell());
        subjectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in subject list panel changed to : '" + newValue + "'");
            onSelectedSubjectChange.accept(newValue);
        });
        selectedSubject.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected subject changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected subject,
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
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Subject} using a {@code SubjectCard}.
     */
    class SubjectListViewCell extends ListCell<Subject> {
        @Override
        protected void updateItem(Subject subject, boolean empty) {
            super.updateItem(subject, empty);

            if (empty || subject == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SubjectCard(subject, getIndex() + 1).getRoot());
            }
        }
    }

}
