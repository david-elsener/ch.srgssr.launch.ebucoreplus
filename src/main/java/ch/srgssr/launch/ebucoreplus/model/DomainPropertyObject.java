package ch.srgssr.launch.ebucoreplus.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainPropertyObject implements DomainProperty {

  private String ontUri;
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
        + ontUri
        + '\''
        + ", description='"
        + description
        + '\''
        + ", domainClass="
        + domainClass.getOntUri()
        + '}';
  }
}
