// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.production;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*To define the format of a container e.g. imf or MXF. It provides information
            on the container / wrapper format in complement to the stream encoding information provided in 'channel',
            (e.g. mp3, wave, Quicktime, ogg).
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerFormat extends Format {
}
