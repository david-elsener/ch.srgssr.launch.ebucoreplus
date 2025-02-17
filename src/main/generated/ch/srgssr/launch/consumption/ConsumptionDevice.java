// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.consumption;

import ch.srgssr.launch.legal.assets.Agent;
import ch.srgssr.launch.legal.assets.Identifier;
import ch.srgssr.launch.production.Essence;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * a device for using media services
 *
 * <pre>
 * Examples:
 * - a smartphone
 * - a smart TV
 * - a fm radio set
 * - a beamer fed with a media signal and projecting onto a screen
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
public class ConsumptionDevice {

    /**
     * The manufacturer of a ConsumptionDevice.
     */
    private Agent consumptionDeviceManufacturer;

    /**
     * The profile a ConsumptionDevice complies with.
     */
    private ConsumptionDeviceProfile compliesWith;

    /**
     * The brand of a ConsumptionDevice.
     */
    private String consumptionDeviceBrand;

    /**
     * a MediaRessource in the form of a simple file or a complex file package for
     * play-out or publishing
     */
    private Essence renders;

    /**
     * A summary of the resource.
     */
    private String description;

    /**
     * The model of a ConsumptionDevice.
     */
    private String consumptionDeviceModel;

    /**
     * To associate an Identifier with an Asset.
     */
    private Identifier identifier;

    /**
     * The designation of the resource.
     */
    private String name;

    /**
     * The operating system of a ConsumptionDevice.
     */
    private String consumptionDeviceOS;

    /**
     * The browser compatible with a ConsumptionDevice.
     */
    private String consumptionDeviceBrowser;
}
