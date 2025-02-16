// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.planning.campaigns;

import java.lang.java.lang.String;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class PublicationPlan {

    private Contract hasBusinessContract;

    private Instant endDateTime;

    private java.lang.String description;

    private ProductionOrder hasAssociatedProductionOrder;

    private Identifier hasIdentifier;

    private PublicationPlan hasPublicationPlanMember;

    private PublicationPlan isMemberOfPublicationPlan;

    private Concept publicationPlanStatus;

    private Asset hasAsset;

    private java.lang.String publicationPlanTitle;

    private java.lang.String name;

    private Agent hasStakeholder;

    private Concept objectType;

    private Instant startDateTime;

    private PublicationEvent hasPublicationEvent;
}
