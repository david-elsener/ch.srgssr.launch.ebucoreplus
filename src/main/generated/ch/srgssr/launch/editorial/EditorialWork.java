// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.editorial;

import ch.srgssr.launch.legal.assets.EditorialObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * an EditorialObject describing a "work of the mind", a piece.
 *
 * <pre>
 * Examples:
 * - a film based on a playbook
 * - a radio drama based on a playbook
 * - a news programme with several items
 * - an item in a news programme
 * - an episode of a series
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EditorialWork extends EditorialObject {

    /**
     * To define a collection / group of media resources, for example a series made
     * of episodes.
     */
    private EditorialGroup editorialGroup;

    /**
     * an EditorialObject describing a "work of the mind", a piece.
     */
    private EditorialWork editorialWork;

    /**
     * The content promotes some other content
     */
    private EditorialWork promotes;
}
