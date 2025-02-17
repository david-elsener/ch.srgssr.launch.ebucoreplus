package ch.srgssr.launch.ebucoreplus.model;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.SchemaEnum;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.semanticweb.owlapi.model.OWLClass;

@Data
@Builder
public class DomainEnumClass implements SchemaObject, SchemaEnum, DomainClassReference {

  private OWLClass owlClass;
  private String uri;
  private String name;
  private String description;
  private String example;
  private DomainModule module;

  @Override
  public List<SchemaField> getFields() {
    return List.of();
  }

  @Override
  public List<SchemaEntity> getInterfaces() {
    return List.of();
  }

  @Override
  public SchemaEntity getSuperclass() {
    return null;
  }

  @Override
  public String getPackage() {
    return module.getPackageName();
  }
}
