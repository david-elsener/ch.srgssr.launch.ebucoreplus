package ch.srgssr.launch.ebucoreplus.generator;

import ch.srgssr.launch.ebucoreplus.model.DomainProperty;
import ch.srgssr.launch.ebucoreplus.model.DomainPropertyObject;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassElementCollectionGenerator;
import com.github.vladislavsevruk.generator.java.generator.FieldAnnotationGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaField;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainClassFieldGenerator implements ClassElementCollectionGenerator {

  private final List<FieldAnnotationGenerator> annotationGenerators;

  @Override
  public List<String> generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
    return schemaObject.getFields().stream().map(field -> generateField(config, field)).toList();
  }

  private String generateField(JavaClassGeneratorConfig config, SchemaField field) {
    var stringBuilder = new StringBuilder();
    if (config.isAddEmptyLineBetweenFields()) {
      stringBuilder.append("\n");
    }
    annotationGenerators.forEach(
        generator -> stringBuilder.append(generator.generate(config, field)));
    addJavaDoc(stringBuilder, (DomainProperty) field);
    return stringBuilder
        .append(config.getIndent().value())
        .append("private ")
        .append(field.getType().getParameterizedDeclaration())
        .append(" ")
        .append(sanitizeFieldName(field.getName()))
        .append(";\n")
        .toString();
  }

  private String sanitizeFieldName(String fieldName) {
    return fieldName.startsWith("has")
        ? Character.toLowerCase(fieldName.charAt(3)) + fieldName.substring(4)
        : fieldName;
  }

  @SuppressWarnings("java:S3457")
  private void addJavaDoc(StringBuilder stringBuilder, DomainProperty domainProperty) {
    var description = getDescription(domainProperty);
    if (description != null) {
      stringBuilder.append("/**\n*%s\n*\n*/".formatted(description));
    }
  }

  private String getDescription(DomainProperty domainProperty) {
    if (domainProperty.getDescription() != null) {
      return domainProperty.getDescription();
    }
    if (domainProperty instanceof DomainPropertyObject domainPropertyObject) {
      return domainPropertyObject.getDomainClassReference().getDescription();
    }
    return null;
  }
}
