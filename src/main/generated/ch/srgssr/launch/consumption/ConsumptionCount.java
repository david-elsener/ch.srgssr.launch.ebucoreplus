// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.consumption;

import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.legal.assets.Instant;
import ch.srgssr.launch.legal.assets.TimelinePoint;
import ch.srgssr.launch.planning.campaigns.Audience;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * the actual number and circumstances of ConsumptionEvents for a single
 * PublicationEvent
 *
 *
 * <pre>
 * Examples:
 * - a 46% market share of French viewers for the football world cup final 2022
 * - a 72% market share of French viewers aged 14 to 29 for the football world cup final 2022
 * - 18.7 million live stream views of the funeral of former pope Benedict in Europe
 * - 36973 views of the youtube-video "Tom Hanks had an ICONIC chat with the Queen" from BBC's "The One Show" 6
 * months aber publishing
 * - 2080 attendees at Stuttgart Liederhalle for a live concert of SWR's symphonic orchestra on April 8, 2022
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class ConsumptionCount {

    /**
     * Describing an end of an interval
     */
    private Instant endDateTime;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * a segment of the potential consumers of media services distinguished by criteria
     * that shape their consumption behaviour, like age, education, sex, social status, residence, attitudes, etc
     */
    private Audience audience;

    /**
     * A precise time point of a media resource
     */
    private TimelinePoint start;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * A precise time point of a media resource
     */
    private TimelinePoint end;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * A point of time or start of an interval
     */
    private Instant startDateTime;

    /**
     * The Audience reach in numbers or percent.
     */
    private String audienceCount;

    /**
     * To identify a ConsumptionEvent related to a PublicationEvent.
     */
    private ConsumptionEvent consumptionEvent;
}
