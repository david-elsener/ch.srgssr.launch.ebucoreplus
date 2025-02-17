// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.production;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*Ancillary data track e.g. Â¨captioning" or "subtitling" in addition
            to video and audio tracks.
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataTrack extends Track {

/**
*To describe the format of data carried in a resource.
*
*/    private DataFormat dataFormat;
}
