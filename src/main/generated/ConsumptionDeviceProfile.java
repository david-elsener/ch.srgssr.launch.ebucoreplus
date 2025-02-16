// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.planning.campaigns;

import java.lang.java.lang.String;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ConsumptionDeviceProfile {

    private java.lang.String name;

    private PublicationPlatform canAccessPublicationPlatform;

    private java.lang.String description;

    private Location hasGeoLocation;

    private boolean internetConnectivity;

    private boolean microphone;

    private boolean videoDisplay;

    private boolean soundOutput;

    private PublicationChannel canAccessPublicationChannel;

    private boolean timeshift;

    private Identifier hasIdentifier;

    private boolean textInput;

    private boolean webcam;

    private Concept objectType;

    private boolean radioTuner;
}
