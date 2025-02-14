package ch.srgssr.launch.ebucoreplus.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainPropertyValue implements DomainProperty {

  private String ontUri;
  private String name;
  private String description;
  private String type;
}
