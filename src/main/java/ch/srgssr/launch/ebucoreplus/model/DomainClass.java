package ch.srgssr.launch.ebucoreplus.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(exclude = {"properties", "subclasses"})
public class DomainClass {

  private String ontUri;
  private String name;
  private String description;
  private DomainModule module;
  private final List<DomainProperty> properties = new ArrayList<>();
  private final List<DomainClass> subclasses = new ArrayList<>();
}
