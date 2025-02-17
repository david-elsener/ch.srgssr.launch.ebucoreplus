// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.production;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * To define the format of AncillaryData such as legacy data used to be carried
 * in vertical blanking intervals. This is provided as free text in an annotation label or as an identifier
 * pointing to a term in a classification scheme.
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AncillaryDataFormat extends DataFormat {
}
