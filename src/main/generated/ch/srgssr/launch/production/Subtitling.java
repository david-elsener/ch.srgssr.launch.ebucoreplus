// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.production;

import ch.srgssr.launch.legal.assets.Agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*To signal the presence of subtitles for translation in alternative
            languages.
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Subtitling extends Track {

/**
*To identify the source of the Subtitling resource.
*
*/    private Agent subtitlingSource;
}
