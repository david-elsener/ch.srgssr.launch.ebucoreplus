// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * a set of properties describing the origin and lifecycle of a data object
 *
 *
 * <pre>
 * Examples:
 * - the creationDate of a data object
 * - the system from which the data object was originally retrieved
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class Provenance {

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * To associate an Agent with a Provenance instance.
     */
    private Agent isAttributedTo;

    /**
     * The instance of an object sourced by the Provenance.
     */
    private Provenance provenanceTarget;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * To indicate the date at which the Asset has been modified.
     */
    private Instant dateModified;

    /**
     * The date of creation of the Asset.
     */
    private Instant dateCreated;
}
