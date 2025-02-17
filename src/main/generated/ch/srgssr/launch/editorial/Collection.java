// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.editorial;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*A group of EditorialObjects. There can be many types of collections for which
            specific sub-classes should be defined. In the worl of archives, A collection corresponds to all items
            belonging to an individual / collector.
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Collection extends EditorialGroup {
}
