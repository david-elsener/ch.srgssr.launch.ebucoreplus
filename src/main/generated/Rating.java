// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.planning.campaigns;

import java.lang.java.lang.String;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Rating {

    private Agent hasRatingProvider;

    private boolean notRated;

    private Identifier hasIdentifier;

    private java.lang.String ratingScaleMax;

    private java.lang.String name;

    private java.lang.String ratingSystemEnvironment;

    private java.lang.String reason;

    private java.lang.String description;

    private java.lang.String ratingSystemName;

    private java.lang.String ratingScaleMin;

    private Asset appliesTo;

    private java.lang.String ratingValue;

    private CountryCode appliesOutOf;

    private Concept objectType;

    private Picture hasPictogram;

    private Logo hasLogo;
}
