package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalFlashcards.ENGLISH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.flashcard.Flashcard;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Flashcard> selectedFlashcard = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedFlashcard));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a flashcard
        guiRobot.interact(() -> selectedFlashcard.set(ENGLISH));
        URL expectedFlashcardUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ENGLISH.getTopic()
                .fullTopic.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedFlashcardUrl, browserPanelHandle.getLoadedUrl());
    }
}
