package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.SubjectCardHandle;
import guitests.guihandles.SubjectListPanelHandle;
import seedu.address.model.subject.Subject;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(SubjectCardHandle expectedCard, SubjectCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedSubject}.
     */
    public static void assertCardDisplaysSubject(Subject expectedSubject, SubjectCardHandle actualCard) {
        assertEquals(expectedSubject.getName().fullName, actualCard.getName());
        assertEquals(expectedSubject.getPhone().value, actualCard.getPhone());
        assertEquals(expectedSubject.getEmail().value, actualCard.getEmail());
        assertEquals(expectedSubject.getAddress().value, actualCard.getAddress());
        assertEquals(expectedSubject.getDeadline().value, actualCard.getDeadline());
        assertEquals(expectedSubject.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code subjectListPanelHandle} displays the details of {@code subjects} correctly and
     * in the correct order.
     */
    public static void assertListMatching(SubjectListPanelHandle subjectListPanelHandle, Subject... subjects) {
        for (int i = 0; i < subjects.length; i++) {
            subjectListPanelHandle.navigateToCard(i);
            assertCardDisplaysSubject(subjects[i], subjectListPanelHandle.getSubjectCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code subjectListPanelHandle} displays the details of {@code subjects} correctly and
     * in the correct order.
     */
    public static void assertListMatching(SubjectListPanelHandle subjectListPanelHandle, List<Subject> subjects) {
        assertListMatching(subjectListPanelHandle, subjects.toArray(new Subject[0]));
    }

    /**
     * Asserts the size of the list in {@code subjectListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(SubjectListPanelHandle subjectListPanelHandle, int size) {
        int numberOfPeople = subjectListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
