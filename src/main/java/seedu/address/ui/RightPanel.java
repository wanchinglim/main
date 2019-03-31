package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.model.flashcard.Flashcard;

/**
 * Panel containing the topic and content of flashcards.
 */

public class RightPanel extends UiPart<Region> {
    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";
    //public static final String SEARCH_PAGE_URL = "https://cs2113-ay1819s2-m11-3.github.io/TopicContentPage.html";
    //public static final String DEFAULT_PAGE = "default.html";
    //public static final String SEARCH_PAGE_URL = "https://dannong.github.io/dannflashcards/TopicContentPage.html";
    //private static final String FXML = "BrowserPanel.fxml";

    private static final String FXML = "RightPanel.fxml";

    @FXML
    private WebView browser;
    @FXML
    private SplitPane rightMostPane;
    @FXML
    private AnchorPane buttonAnchorPane;
    @FXML
    private StackPane contentAnchorPane;
    //private AnchorPane contentAnchorPane;
    @FXML
    private StackPane topicAnchorPane;
    @FXML
    private Label selectedFlashcardTopic;
    @FXML
    private Label selectedFlashcardContent;

    public RightPanel(ObservableValue<Flashcard> selectedFlashcard) {
        super(FXML);

        // Load flashcard page when selected flashcard changes.
        selectedFlashcard.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadFlashcardPage(newValue);
        });

        loadDefaultPage();
    }

    private void loadFlashcardPage(Flashcard flashcard) {
        //loadPage(SEARCH_PAGE_URL + flashcard.getTopic().fullTopic);
        selectedFlashcardTopic.setText(flashcard.getTopic().toString());
        selectedFlashcardContent.setText(flashcard.getContent().toString());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }
}
