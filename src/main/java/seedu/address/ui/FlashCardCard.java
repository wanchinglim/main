package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.FlashCard;

/**
 * An UI component that displays information of a {@code FlashCard}.
 */
public class FlashCardCard extends UiPart<Region> {

    private static final String FXML = "FlashCardListCard.fxml";
    private static final String[] TAG_COLOURS =
        {"white", "black", "orange", "maroon", "pink", "green", "blue", "purple", "navy", "beige"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable subjects cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final FlashCard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label subject;
    @FXML
    private Label id;
    @FXML
    private Label content;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public FlashCardCard(FlashCard flashcard, int displayedIndex) {
        super(FXML);
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        subject.setText(flashcard.getSubject().subject);
        content.setText(flashcard.getContent().content);
        tagger(flashcard);
    }

    /**
     * Returns the color style for {@code tagSubject}'s label.
     */
    private String getTagColourIndex(String tagSubject) {
        return TAG_COLOURS[Math.floorMod(tagSubject.hashCode(), TAG_COLOURS.length)];
    }

    /**
     * Creates the tag labels for {@code flashcard}.
     */
    private void tagger(FlashCard p) {
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
        if (!(other instanceof FlashCardCard)) {
            return false;
        }

        // state check
        FlashCardCard card = (FlashCardCard) other;
        return id.getText().equals(card.id.getText())
                && flashcard.equals(card.flashcard);
    }
}
