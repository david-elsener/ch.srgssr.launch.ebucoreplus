package ch.srgssr.launch.ebucoreplus.model;

import com.github.vladislavsevruk.generator.java.type.SchemaField;

public interface DomainProperty extends SchemaField {

  String getUri();

  String getName();

  String getDescription();

  default boolean isCollection() {
    return getDescription().toLowerCase().contains("list of");
  }

}
