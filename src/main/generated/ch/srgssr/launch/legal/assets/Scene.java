// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import ch.srgssr.launch.editorial.Programme;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*an EditorialSegment incorporated in an EditorialWork and representing a part of a
            story without leaps in time or location
        
*
*<pre>
Examples:
*- the final goodbye of Humphrey Bogart und Ingrid Bergman at the airport of
            Casablanca in the equally named film.
        
</pre>
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Scene extends EditorialSegment {

/**
*an EditorialSegment filmed with one camera without interruption
*
*/    private Shot shot;

/**
*an EditorialWork as a stand-alone piece or an episode of a Serial or Series,
            intended to be consumed as a whole
        
*
*/    private Programme programme;

/**
*a particular instance of a Shot or a Scene amongst a number of similar instances
            for selection during post-production
        
*
*/    private Take take;
}
