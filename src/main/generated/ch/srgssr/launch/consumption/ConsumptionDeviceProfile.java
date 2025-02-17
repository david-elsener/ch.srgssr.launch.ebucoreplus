// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.consumption;

import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.legal.assets.Location;
import ch.srgssr.launch.legal.assets.PublicationChannel;
import ch.srgssr.launch.publication.PublicationPlatform;

import lombok.Data;
import lombok.experimental.Accessors;

/**
*a number of properties of an individual ConsumptionDevice that allows
            classification of the device
        
*
*<pre>
Examples:
*- an Apple smartphone with iOS version > 16
            - a smart TV with screen 50' to 60' and HDR support
            - a VR headset
        
</pre>
*/@Accessors(chain = true)
@Data
public class ConsumptionDeviceProfile {

/**
*The Location of a ConsumptionDevice.
*
*/    private Location geoLocation;

/**
*A flag to indicate if the Service requires a sound output.
        
*
*/    private boolean soundOutput;

/**
*A flag to indicate if the Service requires a webcam.
*
*/    private boolean webcam;

/**
*To indicate if the Service requires text input capability.
        
*
*/    private boolean textInput;

/**
*a facility that allows to connect sources and targets of content through uni- or
            bidirectional channels
        
*
*/    private PublicationPlatform canAccessPublicationPlatform;

/**
*To indicate if the Service requires a microphone.
*
*/    private boolean microphone;

/**
*A flag to indicate if the Service requires timeshift support.
        
*
*/    private boolean timeshift;

/**
*To associate an Identifier with an Asset.
*
*/    private Identifier identifier;

/**
*A summary of the resource.
*
*/    private String description;

/**
*To indicate if the Service requires Internet connectivity.
        
*
*/    private boolean internetConnectivity;

/**
*A flag to indicate if the Service requires a video display.
        
*
*/    private boolean videoDisplay;

/**
*The designation of the resource.
*
*/    private String name;

/**
*To indicate if the Service requires a radio tuner.
*
*/    private boolean radioTuner;

/**
*a connection to exchange content between provider and consumer operated on a
            PublicationPlatform
        
*
*/    private PublicationChannel canAccessPublicationChannel;
}
