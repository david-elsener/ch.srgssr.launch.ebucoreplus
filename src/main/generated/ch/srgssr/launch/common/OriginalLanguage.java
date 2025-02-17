// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.common;

import ch.srgssr.launch.legal.assets.Language;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * The original language in which the EditorialObject or Resource has been
 * created and released. This is provided as free text in an annotation label or as an identifier pointing to a
 * term in a classification scheme.
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OriginalLanguage extends Language {
}
