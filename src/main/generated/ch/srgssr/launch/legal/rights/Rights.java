// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.rights;

import ch.srgssr.launch.common.CountryCode;
import ch.srgssr.launch.legal.assets.Agent;
import ch.srgssr.launch.legal.assets.Asset;
import ch.srgssr.launch.legal.assets.Contact;
import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.legal.assets.Instant;
import ch.srgssr.launch.legal.assets.PublicationService;
import ch.srgssr.launch.legal.assets.TimelinePoint;
import ch.srgssr.launch.legal.contracts.Contract;

import java.net.URI;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The intellectual property or other contractual (e.g. publication) Rights
 * associated to an Asset, a Contract, or a PublicationEvent. Rights originate from a Contract. Rights are
 * associated to EditorialObjects, MediaResource or Essences through the definition of an Asset. Rights have
 * associated RightsHolders.
 */
@Accessors(chain = true)
@Data
public class Rights {

    /**
     * To express an expression of Rights.
     */
    private String rightsExpression;

    /**
     * A link to e.g. a webpage where an expression of the rights can be found and
     * consulted.
     */
    private URI rightsLink;

    /**
     * To identify an Agent (Contact/person or Organisation) having/managing
     * Rights.
     */
    private Agent rightsHolder;

    /**
     * To provide information on a Contact for an Organisation or a physical person
     * (e.g. the agent of an actor).
     */
    private Contact contact;

    /**
     * The Contract from which the Rights orginate.
     */
    private Contract originateFrom;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * A list of country or region codes to which Rights do not apply.
     */
    private List<CountryCode> rightsTerritoryExcludes;

    /**
     * A list of country or region codes to which Rights apply.
     */
    private List<CountryCode> rightsTerritoryIncludes;

    /**
     * A point of time or start of an interval
     */
    private Instant startDateTime;

    /**
     * Describing an end of an interval
     */
    private Instant endDateTime;

    private int publishingEventsLeft;

    /**
     * To identify a Service associated to Rights.
     */
    private PublicationService rightsTargetService;

    /**
     * A precise time point of a media resource
     */
    private TimelinePoint duration;

    /**
     * The Asset to which Rights apply.
     */
    private Asset applyTo;

    /**
     * To specify a set of UsageRestrictions.
     */
    private String rightsUsageRestrictions;
}
