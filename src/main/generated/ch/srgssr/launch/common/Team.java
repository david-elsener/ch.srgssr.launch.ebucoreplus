// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*To define a Team.
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Team extends Organisation {

/**
*To identify the members of a Team
*
*/    private Person teamMember;
}
