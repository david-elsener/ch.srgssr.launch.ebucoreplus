package ch.srgssr.launch.ebucoreplus.model;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.semanticweb.owlapi.model.OWLClass;

@Data
@Builder
@EqualsAndHashCode(exclude = {"properties", "subclasses"})
public class DomainClass implements SchemaObject, SchemaEntity, DomainClassReference {

  private OWLClass owlClass;
  private String uri;
  private String name;
  private String description;
  private String example;
  private DomainModule module;
  private final List<DomainProperty> properties = new ArrayList<>();
  private DomainClass superClass;
  private final List<DomainClass> subclasses = new ArrayList<>();

  @Override
  public List<SchemaField> getFields() {
    return properties.stream().map(SchemaField.class::cast).toList();
  }

  @Override
  public List<SchemaEntity> getInterfaces() {
    return List.of();
  }

  @Override
  public DomainClass getSuperclass() {
    return superClass;
  }

  @Override
  public String getPackage() {
    return module.getPackageName();
  }
}
