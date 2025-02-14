package ch.srgssr.launch.ebucoreplus.model;

public interface DomainProperty {

  String getOntUri();

  String getName();

  String getDescription();

  default boolean isCollection() {
    return getDescription().toLowerCase().contains("list of");
  }
}
