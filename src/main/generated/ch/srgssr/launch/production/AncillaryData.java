// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.production;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Any ancillary data provided with the content other than captioning and
 * subtitling.
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AncillaryData extends DataTrack {

    /**
     * To provide the number of the line on which ancillary data is being carried
     * and the equivalent in the digital domain.
     */
    private int lineNumber;

    /**
     * The Data Identifier word (along with the SDID, if used), indicates the type
     * of ancillary data that the packet corresponds to.
     */
    private int DID;

    /**
     * To specify the type of wrapping.
     */
    private WrappingType wrappingType;

    /**
     * Secondary data identification word for ancillary data. Send mode identifier.
     * An identifier which indicates the transmission timing for closed caption data.
     */
    private int SDID;
}
