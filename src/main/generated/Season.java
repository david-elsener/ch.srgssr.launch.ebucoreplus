// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.planning.campaigns;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Season {

    private int seasonNumber;

    private Programme hasEpisode;

    private Series isSeasonOf;

    private Serial hasSerial;
}
