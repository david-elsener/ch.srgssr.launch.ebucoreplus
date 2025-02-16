// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.planning.campaigns;

import java.lang.java.lang.String;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Provenance {

    private Concept objectType;

    private Identifier hasIdentifier;

    private java.lang.String description;

    private Agent isAttributedTo;

    private Instant dateModified;

    private Provenance hasProvenanceTarget;

    private Instant dateCreated;

    private java.lang.String name;
}
