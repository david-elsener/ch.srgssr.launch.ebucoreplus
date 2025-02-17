package ch.srgssr.launch.ebucoreplus.model;

import com.github.vladislavsevruk.generator.java.type.SchemaObject;

public interface DomainClassReference extends SchemaObject {

  DomainModule getModule();

  String getUri();

  String getDescription();
}
