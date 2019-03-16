package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.StatusBarFooter.TOTAL_SUBJECTS_STATUS;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.SubjectListPanelHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.testutil.TypicalSubjects;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;


/**
 * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class AddressBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected AddressBook getInitialData() {
        return TypicalSubjects.getTypicalAddressBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public SubjectListPanelHandle getSubjectListPanel() {
        return mainWindowHandle.getSubjectListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all subjects in the address book.
     */
    protected void showAllSubjectss() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getSubjectList().size(), getModel().getFilteredSubjectList().size());
    }

    /**
     * Displays all subjects with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showSubjectsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredSubjectList().size() < getModel().getAddressBook().getSubjectList().size());
    }

    /**
     * Selects the subject at {@code index} of the displayed list.
     */
    protected void selectSubject(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getSubjectListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all subjects in the address book.
     */
    protected void deleteAllSubjects() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getSubjectList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same subject objects as {@code expectedModel}
     * and the subject list panel displays the subjects in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new AddressBook(expectedModel.getAddressBook()), testApp.readStorageAddressBook());
        assertListMatching(getSubjectListPanel(), expectedModel.getFilteredSubjectList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code SubjectListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberTotalSubjectsStatus();
        statusBarFooterHandle.rememberSyncStatus();
        getSubjectListPanel().rememberSelectedSubjectCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertFalse(getSubjectListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the subject in the subject list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see SubjectListPanelHandle#isSelectedSubjectCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getSubjectListPanel().navigateToCard(getSubjectListPanel().getSelectedCardIndex());
        String selectedCardName = getSubjectListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getSubjectListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the subject list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see SubjectListPanelHandle#isSelectedSubjectCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getSubjectListPanel().isSelectedSubjectCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isTotalSubjectsStatusChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     * {@code ClockRule#getInjectedClock()}, while the save location and the total subject
     *      * list remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isTotalSubjectsStatusChanged());
    }

    /**
     * Asserts that the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, and total subjects was changed to match the total
     * number of subjects in the address book, while the save location remains the same.
     */
    protected void assertStatusBarChangedExceptSaveLocation() {
        StatusBarFooterHandle handle = getStatusBarFooter();

        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());

        final int totalSubjects = testApp.getModel().getAddressBook().getSubjectList().size();
        assertEquals(String.format(TOTAL_SUBJECTS_STATUS, totalSubjects), handle.getTotalSubjectsStatus());

        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getSubjectListPanel(), getModel().getFilteredSubjectList());
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
        assertEquals(String.format(TOTAL_SUBJECTS_STATUS, getModel().getAddressBook().getSubjectList().size()),
                getStatusBarFooter().getTotalSubjectsStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
