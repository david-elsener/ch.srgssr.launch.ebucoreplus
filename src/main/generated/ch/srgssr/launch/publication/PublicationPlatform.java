// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.publication;

import ch.srgssr.launch.common.Logo;
import ch.srgssr.launch.consumption.ConsumptionDeviceProfile;
import ch.srgssr.launch.legal.assets.Agent;
import ch.srgssr.launch.legal.assets.AlternativeTitle;
import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.legal.assets.Instant;
import ch.srgssr.launch.legal.assets.PublicationChannel;
import ch.srgssr.launch.legal.assets.PublicationService;
import ch.srgssr.launch.planning.campaigns.Audience;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * a facility that allows to connect sources and targets of content through uni- or
 * bidirectional channels
 *
 *
 * <pre>
 * Examples:
 * - the Eutelsat DVB-S broadcasting platform
 * - the youtube video on demand platform
 * - all servers accessible via internet protocols and offering / taking content encoded in HTML or similar
 * - Fortnite, Decentraland or Roblox as metaverses
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class PublicationPlatform {

    /**
     * To establish a relation to publication services.
     */
    private PublicationService relatedPublicationService;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * A Consumer Account for a particular Service.
     */
    private Account accountingTo;

    /**
     * To identify the Agent (Contact/person or Organisation) who owns a Service
     * operating a PublicationChannel.
     */
    private Agent isOwnedBy;

    /**
     * To associate a TargetAudience (e.g. for parental guiddance or targeting a
     * particular social group) with a EditorialObject
     */
    private Audience targetAudience;

    /**
     * The count of and relation to the real audience.
     */
    private Audience consumptionCount;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * To identify the PublicationEvents available through a Service.
     */
    private PublicationEvent offers;

    /**
     * Describing an end of an interval
     */
    private Instant endDateTime;

    /**
     * Logos can be used in a variety of contexts. Logo can be associated with an
     * Organisation or a Service or a PublicationChannel.
     */
    private Logo logo;

    /**
     * a service by which a publishing organisation or person publishes its content and
     * offers access to consumers
     */
    private PublicationService offersService;

    /**
     * A point of time or start of an interval
     */
    private Instant startDateTime;

    /**
     * To define the profile of a ConsumptionDevice.
     */
    private ConsumptionDeviceProfile supportsConsumptionDeviceProfile;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * a connection to exchange content between provider and consumer operated on a
     * PublicationPlatform
     */
    private PublicationChannel publicationChannel;

    private AlternativeTitle alternativeTitle;
}
