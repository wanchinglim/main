package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.Flashcard;

public class RightPage extends UiPart<Region> {
    //public static final URL DEFAULT_PAGE =
    //        requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    //public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";
    //public static final String SEARCH_PAGE_URL = "https://cs2113-ay1819s2-m11-3.github.io/TopicContentPage.html";
    //public static final String DEFAULT_PAGE = "default.html";
    //public static final String SEARCH_PAGE_URL = "https://dannong.github.io/dannflashcards/TopicContentPage.html";
    //private static final String FXML = "BrowserPanel.fxml";
    private static final String FXML = "RightPage.fxml";
    //public final Flashcard flashcard;

    //@FXML
    //private WebView browser;
    @FXML
    private SplitPane rightMostPane;
    @FXML
    private AnchorPane buttonAnchorPane;
    @FXML
    private AnchorPane contentAnchorPane;

    public RightPage(ObservableList<Flashcard> flashcardList) {
    super(FXML);
    init();
    }

    private void init() {
        //rightPane.maxHeightProperty().bind(rightMostPane.heightProperty().multiply(0.6));
        buttonAnchorPane.maxWidthProperty().bind(rightMostPane.heightProperty().multiply(0.4));
        contentAnchorPane.maxHeightProperty().bind(rightMostPane.widthProperty().multiply(0.5));
    }
}
    //public RightPage(ObservableValue<Flashcard> selectedFlashcard) {
//    public RightPage(Flashcard flashcard) {
//        super(FXML);
//        this.flashcard = flashcard;
//        if (flashcard != null && flashcard.getTopic() != null) {
//            selectedTopic.setText(flashcard.getTopic().toString());
//            selectedContent.setText(flashcard.getContent().toString());
//        } else {
//            selectedTopic.setText("Welcome to FlashCards! Add a flashcard to continue.");
//            selectedContent.setText("");
//        }
//    }
//}
//        // To prevent triggering events for typing inside the loaded Web page.
//        getRoot().setOnKeyPressed(Event::consume);
//
//        // Load flashcard page when selected flashcard changes.
//        selectedFlashcard.addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                loadDefaultPage();
//                return;
//            }
//            loadFlashcardPage(newValue);
//        });
//
//        loadDefaultPage();
        //registerAsAnEventHandler(this);
//    }

//    public static String getFlashcardPageUrl() {
//        return SEARCH_PAGE_URL;
//    }
//
//    public String encodeString (String arg) {
//        try {
//            String encodedString = URLEncoder.encode(arg, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
//            return encodedString;
//        } catch (UnsupportedEncodingException uee) {
//            logger.warning("Encoding not supported.");
//            return "";
//        }
//    }
//
//    private String formatFlashcardPageUrl (Flashcard flashcard) {
//        String queryString = "?topic=" + encodeString(flashcard.getTopic().toString())
//                + "&content=" + encodeString(flashcard.getContent().toString());
//        return SEARCH_PAGE_URL + queryString;
//    }

//    private void loadFlashcardPage(Flashcard flashcard) {
//        try {
//            URL searchPage = new URL(formatFlashcardPageUrl(flashcard));
//            loadPage(searchPage.toExternalForm());
//        } catch (MalformedURLException mue) {
//            logger.warning("Flashcard page has invalid parameters. Default page will be loaded.");
//            loadDefaultPage();
//        }
//        loadPage(SEARCH_PAGE_URL + flashcard.getTopic().fullTopic);
//    }

//    public void loadPage(String url) {

//        Platform.runLater(() -> browser.getEngine().load(url));
//    }

//    /**
//     * Loads a default HTML file with a background that matches the general theme.
//     */
//    private void loadDefaultPage() {
       // URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
       // loadPage(defaultPage.toExternalForm());
//        loadPage(DEFAULT_PAGE.toExternalForm());
//    }

//    public void freeResources() {
//        browser = null;
//    }
//
//    @Subscribe
//    private void handleFlashcardPanelSelectionChangedEvent (FlashcardPanelSelectionChangedEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        loadFlashcardPage(event.getNewSelection());
//    }

//}



//
//
//package seedu.address.ui;
//
//        import java.util.Objects;
//        import java.util.function.Consumer;
//        import java.util.logging.Logger;
//        import javafx.event.Event;
//        import javafx.fxml.FXML;
//        import javafx.scene.control.Label;
//        import javafx.scene.layout.Region;
//        import javafx.stage.Stage;
//        import seedu.address.commons.core.EventsCenter;
//        import seedu.address.commons.core.LogsCenter;
//        import seedu.address.commons.events.ui.ShowContentRequestEvent;
//        import seedu.address.commons.events.ui.ShowTopicRequestEvent;
//        import seedu.address.logic.Logic;
//        import seedu.address.model.flashcard.Flashcard;
//        import javafx.beans.value.ObservableValue;
//        import javafx.collections.ObservableList;
//        import javafx.event.ActionEvent;
//        import javafx.event.Event;
//        import javafx.fxml.FXML;
//        import javafx.scene.control.Label;
//        import javafx.scene.control.ListCell;
//        import javafx.scene.control.ListView;
//        import javafx.scene.layout.Region;
//        import javafx.stage.Stage;
//        import seedu.address.commons.core.EventsCenter;
//        import seedu.address.commons.core.GuiSettings;
//        import seedu.address.commons.core.LogsCenter;
//        import seedu.address.logic.Logic;
//        import seedu.address.model.flashcard.Content;
//        import seedu.address.model.flashcard.Flashcard;
//        import seedu.address.model.flashcard.Topic;
//        import seedu.address.ui.HelpWindow;
//        import seedu.address.commons.events.ui.ShowContentRequestEvent;
//        import seedu.address.commons.events.ui.ShowTopicRequestEvent;
//
///**
// * Panel containing the topic and content of flashcards.
// */
//
//public class RightPage extends UiPart<Region> {
//    private static final String FXML = "ButtonPane.fxml";
//
//    public static final String FORMAT_TOPIC = "Topic: %s";
//    public static final String FORMAT_CONTENT = "Content: %s";
//
//    private Stage primaryStage;
//    private Logic logic;
//
//    private final Logger logger = LogsCenter.getLogger(getClass());
//
//    @javafx.fxml.FXML
//    private Label selectedTopic;
//    @FXML
//    private Label selectedContent;
//
//    private Flashcard currentFlashcard;
//
//    //public RightPage(ObservableValue<Topic> selectedTopic, ObservableValue<Content> selectedContent,
//    // Consumer<Topic> onSelectedTopicChange, Consumer<Content> onSelectedContentChange) {
//
//    public RightPage() {
//        super(FXML);
//        getRoot().setOnKeyPressed(Event::consume);
//        registerAsAnEventHandler(this);
//        //currentFlashcard.addListener
//    }
//
//    private void loadFlashcardPage(Flashcard card) {
//        logger.info("Loading");
//        selectedTopic.setText(String.format(FORMAT_TOPIC, card.getTopic()));
//        selectedContent.setText(String.format(FORMAT_CONTENT, card.getContent()));
//    }
//
//    /**
//     * Handles the change to Topic display.
//     */
//
//    @FXML
//    public void handleChangeToTopic() {
//        EventsCenter.getInstance().post(new ShowTopicRequestEvent());
//        //clearResultDisplay();
//    }
//
//    /**
//     * Handles the change to Content display.
//     */
//
//    @FXML
//    public void handleChangeToContent() {
//        EventsCenter.getInstance().post(new ShowContentRequestEvent());
//        //clearResultDisplay();
//    }
//
//
//
//
//}
//
