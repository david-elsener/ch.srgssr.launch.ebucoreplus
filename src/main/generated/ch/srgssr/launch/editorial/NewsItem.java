// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.editorial;

import ch.srgssr.launch.legal.assets.Dopesheet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*A NewsItem aggregates all information about a particular news event.
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsItem extends Item {

/**
*The dopesheet of a NewsItem.
*
*/    private Dopesheet dopesheet;
}
