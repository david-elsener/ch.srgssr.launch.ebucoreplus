// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import ch.srgssr.launch.common.Character;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * To provide lines of text extracted from or additional to the resource.
 */
@Accessors(chain = true)
@Data
public class TextLine {

    /**
     * To identify the source of a TextLine.
     */
    private Agent textLineSource;

    /**
     * The coordinates on an horizontal axis of the position of the top left corner
     * of the text box containing the TextLine.
     */
    private int textLineBoxTopLeftCornerPixelNumber;

    /**
     * To associate a concept with an Agent (e.g. Person or Character).
     */
    private Agent relatedAgent;

    /**
     * To provide the content of a text line.
     */
    private String textLineContent;

    /**
     * To associate a concept with a Scene
     */
    private Scene relatedScene;

    /**
     * The height of the text box containing the TextLine.
     */
    private int textLineBoxHeight;

    /**
     * The width of the text box containing the TextLine.
     */
    private int textLineBoxWidth;

    /**
     * The coordinates on a vertical axis of the position of the top left corner of
     * the text box containing the TextLine.
     */
    private int textLineBoxTopLeftCornerLineNumber;

    /**
     * A precise time point of a media resource
     */
    private TimelinePoint end;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * The order in which a text line can be found e.g. in a scene.
     */
    private String textLineOrder;

    /**
     * The coordinates on a vertical axis of the position of the top left corner of
     * the box containing the Artefact.
     */
    private int artefactBoxTopLeftCornerLineNumber;

    /**
     * A precise time point of a media resource
     */
    private TimelinePoint start;

    /**
     * The height of the box containing the Artefact.
     */
    private int artefactBoxHeight;

    /**
     * The coordinates on an horizontal axis of the position of the top left corner
     * of the box containing the Artefact.
     */
    private int artefactBoxTopLeftCornerPixelNumber;

    /**
     * To identify a Character related to a concept
     */
    private Character relatedCharacter;

    /**
     * The width of the box containing the Artefact.
     */
    private int artefactBoxWidth;
}
