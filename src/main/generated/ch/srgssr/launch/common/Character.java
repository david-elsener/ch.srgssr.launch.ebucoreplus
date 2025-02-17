// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.common;

import ch.srgssr.launch.legal.assets.Agent;
import ch.srgssr.launch.legal.assets.Animal;
import ch.srgssr.launch.legal.assets.Artefact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * E.g. a fictitious contact / person.
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Character extends Person {

    /**
     * To identify a Contact/Person being fictitious.
     */
    private Person isFictitiousPerson;

    /**
     * Character depicted by an Artifact
     */
    private Artefact isEmbodiedBy;

    /**
     * Relationship between a fictional character and the actor playing the part.
     */
    private Agent isPlayedBy;

    /**
     * Relationship between a fictional character and the animal that is depicted in
     * the scene.
     */
    private Animal isPortrayedBy;

    /**
     * Character, animated by a persion using an artifact
     */
    private Person isAnimatedBy;
}
