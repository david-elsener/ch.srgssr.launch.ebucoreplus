// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import ch.srgssr.launch.common.Logo;
import ch.srgssr.launch.planning.campaigns.Audience;
import ch.srgssr.launch.publication.Account;
import ch.srgssr.launch.publication.PublicationEvent;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * a service by which a publishing organisation or person publishes its content and
 * offers access to consumers
 *
 *
 * <pre>
 * Examples:
 * - the linear video service named "BBC One"
 * - the video on demand service of Netflix
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class PublicationService {

    /**
     * A Consumer Account for a particular Service.
     */
    private Account accountingTo;

    private AlternativeTitle alternativeTitle;

    /**
     * To establish a relation to publication services.
     */
    private PublicationService relatedPublicationService;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * To identify the Agent (Contact/person or Organisation) who owns a Service
     * operating a PublicationChannel.
     */
    private Agent isOwnedBy;

    /**
     * To identify the PublicationEvents available through a Service.
     */
    private PublicationEvent offers;

    /**
     * The count of and relation to the real audience.
     */
    private Audience consumptionCount;

    /**
     * Describing an end of an interval
     */
    private Instant endDateTime;

    /**
     * A point of time or start of an interval
     */
    private Instant startDateTime;

    /**
     * The genre of content associated with the Service.
     */
    private Genre serviceGenre;

    /**
     * To associate a TargetAudience (e.g. for parental guiddance or targeting a
     * particular social group) with a EditorialObject
     */
    private Audience targetAudience;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * Logos can be used in a variety of contexts. Logo can be associated with an
     * Organisation or a Service or a PublicationChannel.
     */
    private Logo logo;
}
