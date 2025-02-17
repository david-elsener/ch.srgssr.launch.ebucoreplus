// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * a unique string associated to an entity
 *
 * <pre>
 * Examples:
 * - "ISAN 0000-0001-8947-0000-8-0000-0000-D" is an ISAN Identifier
 * - "10.5240/EA73-79D7-1B2B-B378-3A73-M" is an EIDR Identifier for the movie "Blade Runner"
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class Identifier {

    /**
     * To identify the issuer of an identifier.
     */
    private Agent isIssuedBy;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * The date of creation of the Asset.
     */
    private Instant dateCreated;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * To provide the value attribued to an Identifier. Range: string or anyURI.
     */
    private String identifierValue;
}
