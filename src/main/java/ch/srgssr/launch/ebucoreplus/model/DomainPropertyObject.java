package ch.srgssr.launch.ebucoreplus.model;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.sequence.ListSchemaEntity;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DomainPropertyObject implements DomainProperty {

  private String uri;
  private String name;
  private String description;
  private DomainClassReference domainClassReference;

  @Override
  public String toString() {
    return "DomainPropertyObject{"
        + "name='"
        + name
        + '\''
        + ", ontUri='"
        + uri
        + '\''
        + ", description='"
        + description
        + '\''
        + ", domainClass="
        + Optional.ofNullable(domainClassReference).map(DomainClassReference::getUri).orElse(null)
        + '}';
  }

  @Override
  public SchemaEntity getType() {
    if (description != null && description.toLowerCase().contains("list of")) {
      return new DefaultListEntity(domainClassReference);
    }
    return domainClassReference;
  }

  private static class DefaultListEntity extends ListSchemaEntity {

    public DefaultListEntity(DomainClassReference domainClassReference) {
      super(domainClassReference);
    }
  }
}
