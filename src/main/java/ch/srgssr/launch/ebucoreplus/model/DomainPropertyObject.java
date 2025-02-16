package ch.srgssr.launch.ebucoreplus.model;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DomainPropertyObject implements DomainProperty {

  private String uri;
  private String name;
  private String description;
  private DomainClass domainClass;

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
        + Optional.ofNullable(domainClass).map(DomainClass::getUri).orElse(null)
        + '}';
  }

  @Override
  public SchemaEntity getType() {
    return domainClass;
  }
}
