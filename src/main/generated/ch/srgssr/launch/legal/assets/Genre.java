// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.legal.assets;

import ch.srgssr.launch.common.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*This class shall be used to provide information on the genre of the
            EditorialObject. This is provided as free text in an annotation label or as an identifier pointing to a term
            in a classification scheme e.g. http://www.ebu.ch/metadata/ontologies/skos/ebu_ContentGenreCS.rdf or
            http://www.ebu.ch/metadata/ontologies/skos/ebu_EditorialFormatCodeCS.rdf.
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Genre extends Type {
}
