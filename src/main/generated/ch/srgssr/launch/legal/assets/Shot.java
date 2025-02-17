// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*an EditorialSegment filmed with one camera without interruption
*
*<pre>
Examples:
*- a part of the final Scene of "Casablance" showing Humphrey Bogart from behind
            while watching Ingrid Bergman and Paul Henreid walking to the plane, filmed from one camera without
            interruption
        
</pre>
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Shot extends EditorialSegment {

/**
*a particular instance of a Shot or a Scene amongst a number of similar instances
            for selection during post-production
        
*
*/    private Take take;

/**
*an EditorialSegment incorporated in an EditorialWork and representing a part of a
            story without leaps in time or location
        
*
*/    private Scene scene;
}
