package ch.srgssr.launch.ebucoreplus.generator;

import ch.srgssr.launch.ebucoreplus.model.DomainEnumClass;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.generator.declaration.BaseDeclarationGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import java.util.List;

public class EnumClassDeclarationGenerator extends BaseDeclarationGenerator {

  public EnumClassDeclarationGenerator(List<ClassElementGenerator> enumAnnotationGenerators) {
    super(enumAnnotationGenerators);
  }

  /** {@inheritDoc} */
  @Override
  public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
    StringBuilder stringBuilder = new StringBuilder("\n");
    addJavaDoc(stringBuilder, (DomainEnumClass) schemaObject);
    addAnnotations(stringBuilder, config, schemaObject);
    stringBuilder.append("public enum ").append(schemaObject.getName());
    addInterfaces(stringBuilder, schemaObject);
    stringBuilder.append(" {\n");
    return stringBuilder.toString();
  }

  @SuppressWarnings("java:S3457")
  private void addJavaDoc(StringBuilder stringBuilder, DomainEnumClass enumClass) {
    var example =
        enumClass.getExample() != null
            ? "<pre>\nExamples:\n*%s\n</pre>".formatted(enumClass.getExample())
            : "";
    stringBuilder.append("/**\n*%s\n*\n*%s\n*/".formatted(enumClass.getDescription(), example));
  }
}
