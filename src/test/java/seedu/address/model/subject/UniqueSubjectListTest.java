package seedu.address.model.subject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalSubjects.ALICE;
import static seedu.address.testutil.TypicalSubjects.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.subject.exceptions.DuplicateSubjectException;
import seedu.address.model.subject.exceptions.SubjectNotFoundException;
import seedu.address.testutil.SubjectBuilder;

public class UniqueSubjectListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();

    @Test
    public void contains_nullSubject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.contains(null);
    }

    @Test
    public void contains_subjectNotInList_returnsFalse() {
        assertFalse(uniqueSubjectList.contains(ALICE));
    }

    @Test
    public void contains_subjectInList_returnsTrue() {
        uniqueSubjectList.add(ALICE);
        assertTrue(uniqueSubjectList.contains(ALICE));
    }

    @Test
    public void contains_subjectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSubjectList.add(ALICE);
        Subject editedAlice = new SubjectBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueSubjectList.contains(editedAlice));
    }

    @Test
    public void add_nullSubject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.add(null);
    }

    @Test
    public void add_duplicateSubject_throwsDuplicateSubjectException() {
        uniqueSubjectList.add(ALICE);
        thrown.expect(DuplicateSubjectException.class);
        uniqueSubjectList.add(ALICE);
    }

    @Test
    public void setSubject_nullTargetSubject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.setSubject(null, ALICE);
    }

    @Test
    public void setSubject_nullEditedSubject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.setSubject(ALICE, null);
    }

    @Test
    public void setSubject_targetSubjectNotInList_throwsSubjectNotFoundException() {
        thrown.expect(SubjectNotFoundException.class);
        uniqueSubjectList.setSubject(ALICE, ALICE);
    }

    @Test
    public void setSubject_editedSubjectIsSameSubject_success() {
        uniqueSubjectList.add(ALICE);
        uniqueSubjectList.setSubject(ALICE, ALICE);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(ALICE);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubject_editedSubjectHasSameIdentity_success() {
        uniqueSubjectList.add(ALICE);
        Subject editedAlice = new SubjectBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueSubjectList.setSubject(ALICE, editedAlice);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(editedAlice);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubject_editedSubjectHasDifferentIdentity_success() {
        uniqueSubjectList.add(ALICE);
        uniqueSubjectList.setSubject(ALICE, BOB);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(BOB);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubject_editedSubjectHasNonUniqueIdentity_throwsDuplicateSubjectException() {
        uniqueSubjectList.add(ALICE);
        uniqueSubjectList.add(BOB);
        thrown.expect(DuplicateSubjectException.class);
        uniqueSubjectList.setSubject(ALICE, BOB);
    }

    @Test
    public void remove_nullSubject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.remove(null);
    }

    @Test
    public void remove_subjectDoesNotExist_throwsSubjectNotFoundException() {
        thrown.expect(SubjectNotFoundException.class);
        uniqueSubjectList.remove(ALICE);
    }

    @Test
    public void remove_existingSubject_removesSubject() {
        uniqueSubjectList.add(ALICE);
        uniqueSubjectList.remove(ALICE);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubjects_nullUniqueSubjectList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.setSubjects((UniqueSubjectList) null);
    }

    @Test
    public void setSubjects_uniqueSubjectList_replacesOwnListWithProvidedUniqueSubjectList() {
        uniqueSubjectList.add(ALICE);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(BOB);
        uniqueSubjectList.setSubjects(expectedUniqueSubjectList);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubjects_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueSubjectList.setSubjects((List<Subject>) null);
    }

    @Test
    public void setSubjects_list_replacesOwnListWithProvidedList() {
        uniqueSubjectList.add(ALICE);
        List<Subject> subjectList = Collections.singletonList(BOB);
        uniqueSubjectList.setSubjects(subjectList);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(BOB);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubjects_listWithDuplicateSubjects_throwsDuplicateSubjectException() {
        List<Subject> listWithDuplicateSubjects = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateSubjectException.class);
        uniqueSubjectList.setSubjects(listWithDuplicateSubjects);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueSubjectList.asUnmodifiableObservableList().remove(0);
    }
}
