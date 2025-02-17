// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.contracts;

import ch.srgssr.launch.legal.assets.Agent;
import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.legal.assets.Resource;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * an agreement between parties or a law concerning the rights and duties in the
 * creation or publication of MediaResources
 *
 *
 * <pre>
 * Examples:
 * - a contract between a PSM and a production company for the delivery of a series
 * - a law about copyrights of news articles
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class Contract {

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * To identify Contract related costs.
     */
    private ContractCost contractRelatedCost;

    /**
     * A Contract used as a template for another Contract.
     */
    private Contract contractTemplate;

    /**
     * A link to a location where information can be found about a Contract.
     */
    private Resource contractLink;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * To identify the different parties involved mentioned in Contract terms.
     */
    private Agent contractualParty;
}
