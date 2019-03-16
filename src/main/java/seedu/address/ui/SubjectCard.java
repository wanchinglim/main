package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.subject.Subject;

/**
 * An UI component that displays information of a {@code Subject}.
 */
public class SubjectCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOURS =
        {"white", "black", "orange", "maroon", "pink", "green", "blue", "purple", "navy", "beige"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Subject subject;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public SubjectCard(Subject subject, int displayedIndex) {
        super(FXML);
        this.subject = subject;
        id.setText(displayedIndex + ". ");
        name.setText(subject.getName().fullName);
        phone.setText(subject.getPhone().value);
        address.setText(subject.getAddress().value);
        email.setText(subject.getEmail().value);
        deadline.setText(subject.getDeadline().value);
        tagger(subject);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColourIndex(String tagName) {
        return TAG_COLOURS[Math.floorMod(tagName.hashCode(), TAG_COLOURS.length)];
    }

    /**
     * Creates the tag labels for {@code subject}.
     */
    private void tagger(Subject p) {
        p.getTags().forEach(tagT -> {
            Label tagLabel = new Label(tagT.tagName);
            tagLabel.getStyleClass().add(getTagColourIndex(tagT.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SubjectCard)) {
            return false;
        }

        // state check
        SubjectCard card = (SubjectCard) other;
        return id.getText().equals(card.id.getText())
                && subject.equals(card.subject);
    }
}
