// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import java.net.URI;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * a Thing used in a ProductionJob, often visible and deliberately chosen for its
 * value to the meaning of the content
 *
 *
 * <pre>
 * Examples:
 * - the costumes and props in a movie
 * - the non-dynamic backdrop, chairs, table in a talk show
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class Artefact {

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * To specify a model of an Artefact.
     */
    private String artefactModel;

    /**
     * To specify a reference of an Artefact.
     */
    private URI artefactReference;

    /**
     * To identify a Resource associated with an Asset or an EditorialObject or a
     * PublicationEvent or another Resource.
     */
    private Resource relatedResource;

    /**
     * To specify a website where more information can be found on the Artefact.
     */
    private URI artefactWebsite;

    /**
     * To provide information on the usage history of an Artefact.
     */
    private String artefactUsageHistory;

    /**
     * The date when an Artefact was sold.
     */
    private Instant artefactDateOfSell;

    /**
     * To identify a supplier of an Artefact.
     */
    private Agent artefactSupplier;

    /**
     * To associate an Artefact/Prop or else with a Location.
     */
    private Location artefactRelatedLocation;

    /**
     * To flag the availability of an Artefact.
     */
    private boolean artefactAvailability;

    /**
     * To specify the brand of an Artefact.
     */
    private String artefactBrand;

    /**
     * The Agent who bought the Artefact.
     */
    private Agent artefactBuyer;

    /**
     * A location assosiated with the object or consept
     */
    private Location location;

    /**
     * To identify the retailer of an Artefact.
     */
    private Agent artefactRetailer;

    /**
     * To provide a contextual comment about an Artefact.
     */
    private String artefactComment;

    /**
     * To specify the currency into which the price of an Artefact is expressed.
     */
    private CurrencyCode artefactPriceCurrency;

    /**
     * To associate a concept with an Agent (e.g. Person or Character).
     */
    private Agent relatedAgent;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * To provide the colour(s) of an Artefact.
     */
    private String artefactColour;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * The date when an Artefact was purchased. .
     */
    private Instant artefactDateOfPurchase;

    /**
     * To associate an Artefact/Prop or else with a physical resource.
     */
    private PhysicalResource artefactRelatedPhysicalResource;

    /**
     * To specifythe price of an Artefact.
     */
    private String artefactPriceAmount;

    /**
     * To identify related EditorialObjects.
     */
    private EditorialObject relatedEditorialObject;

    /**
     * To specify the period associated with an Artefact.
     */
    private String artefactPeriod;

    /**
     * To specify the style associated with an Artefact.
     */
    private String artefactStyle;

    /**
     * To identify an Agent involved in the creation of the Resource or
     * EditorialObject.
     */
    private Involvement creator;

    /**
     * To identify the owner of an Artefact.
     */
    private Agent artefactOwner;
}
