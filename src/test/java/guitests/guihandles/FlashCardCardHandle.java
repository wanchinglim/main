package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.FlashCard;

/**
 * Provides a handle to a flashcard card in the flashcard list panel.
 */
public class FlashCardCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String SUBJECT_FIELD_ID = "#sub";
    private static final String CONTENT_FIELD_ID = "#cont";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label subLabel;
    private final Label contLabel;
    private final List<Label> tagLabels;

    public FlashCardCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        subLabel = getChildNode(SUBJECT_FIELD_ID);
        contLabel = getChildNode(CONTENT_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getSubject() {
        return subLabel.getText();
    }

    public String getContent() {
        return contLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code flashcard}.
     */
    public boolean equals(FlashCard flashcard) {
        return getSubject().equals(flashcard.getSubject().subject)
                && getContent().equals(flashcard.getContent().content)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(flashcard.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
