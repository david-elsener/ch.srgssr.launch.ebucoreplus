// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.common;

import ch.srgssr.launch.production.Format;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * The definition of the container if available as a MIME type. This is provided
 * as free text in an annotation label or as an identifier pointing to a term in a classification scheme. For
 * more information: http://www.iana.org/assignments/media-types/application/index.html.
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerMimeType extends Format {
}
