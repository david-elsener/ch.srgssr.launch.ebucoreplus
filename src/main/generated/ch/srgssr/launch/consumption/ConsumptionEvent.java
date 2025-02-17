// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.consumption;

import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.legal.assets.Instant;
import ch.srgssr.launch.legal.assets.ResonanceEvent;
import ch.srgssr.launch.legal.assets.TimelinePoint;
import ch.srgssr.launch.production.Essence;

import lombok.Data;
import lombok.experimental.Accessors;

/**
*the event of a Consumer consuming a media service
*
*<pre>
Examples:
*- a person watching a news show on linear tv
            - a family listening to linear radio while having breakfast at home
            - a couple on their living room couch streaming a movie from a VoD service
        
</pre>
*/@Accessors(chain = true)
@Data
public class ConsumptionEvent {

/**
*the Essence that has been consumed during a ConsumptionEvent.
        
*
*/    private Essence consumesEssence;

/**
*A link between a ResonanceEvent and a ConsumptionEvent.
*
*/    private ResonanceEvent resultsIn;

/**
*To identify related ConsumptionEvents.
*
*/    private ConsumptionEvent relatedConsumptionEvent;

/**
*To associate an Identifier with an Asset.
*
*/    private Identifier identifier;

/**
*To identify a ConsumptionLicence required by a ConsumptionEvent.
        
*
*/    private ConsumptionLicence requiresLicence;

/**
*The date and time point at which consumption has resumed.
        
*
*/    private TimelinePoint consumptionEventResumePoint;

/**
*The ConsumptionDevice used during a ConsumptionEvent.
*
*/    private ConsumptionDevice device;

/**
*A point of time or start of an interval
*
*/    private Instant startDateTime;

/**
*A precise time point of a media resource
*
*/    private TimelinePoint start;

/**
*The designation of the resource.
*
*/    private String name;

/**
*A precise time point of a media resource
*
*/    private TimelinePoint end;

/**
*A summary of the resource.
*
*/    private String description;

/**
*Describing an end of an interval
*
*/    private Instant endDateTime;
}
