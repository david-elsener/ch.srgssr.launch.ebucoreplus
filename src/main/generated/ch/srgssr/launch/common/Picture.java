// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.common;

import ch.srgssr.launch.legal.assets.Resource;
import ch.srgssr.launch.production.EncodingFormat;
import ch.srgssr.launch.production.ImageCodec;
import ch.srgssr.launch.production.ImageFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * a Resource in the form of an encoded picture
 *
 * <pre>
 * Examples:
 * - a JPEG file of a photo, a logo, a graphic, ...
 * - a PNG file of a photo, a logo, a graphic, ...
 *
 * </pre>
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Picture extends Resource {

    /**
     * To specify the codec of an Image.
     */
    private ImageCodec imageCodec;

    /**
     * To specify the format of an Image.
     */
    private ImageFormat imageFormat;

    /**
     * To describe any encoding format use to produce content.
     */
    private EncodingFormat encodingFormat;

    /**
     * The unit used to measure the height of a frame.
     */
    private String frameHeightUnit;

    /**
     * The height of a video frame.
     */
    private int frameHeight;

    /**
     * The width of a video frame.
     */
    private int frameWidth;

    /**
     * To specify the aspect ratio.
     */
    private String aspectRatio;

    /**
     * The unit used to measure the width of a frame.
     */
    private String frameWidthUnit;
}
