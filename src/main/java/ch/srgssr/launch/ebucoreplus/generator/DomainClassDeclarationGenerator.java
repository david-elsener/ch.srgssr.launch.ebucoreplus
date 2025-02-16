package ch.srgssr.launch.ebucoreplus.generator;

import ch.srgssr.launch.ebucoreplus.model.DomainClass;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.generator.declaration.BaseDeclarationGenerator;
import com.github.vladislavsevruk.generator.java.type.SchemaObject;
import java.util.List;

public class DomainClassDeclarationGenerator extends BaseDeclarationGenerator {

  public DomainClassDeclarationGenerator(List<ClassElementGenerator> classAnnotationGenerators) {
    super(classAnnotationGenerators);
  }

  @Override
  public String generate(JavaClassGeneratorConfig config, SchemaObject schemaObject) {
    var domainClass = (DomainClass) schemaObject;

    var stringBuilder = new StringBuilder("\n");
    addJavaDoc(stringBuilder, domainClass);
    addAnnotations(stringBuilder, config, schemaObject);
    stringBuilder.append("public class ").append(schemaObject.getParameterizedDeclaration());
    if (schemaObject.hasSuperclass()) {
      stringBuilder
          .append(" extends ")
          .append(schemaObject.getSuperclass().getParameterizedDeclaration());
    }
    addInterfaces(stringBuilder, schemaObject);
    stringBuilder.append(" {\n");
    return stringBuilder.toString();
  }

  @SuppressWarnings("java:S3457")
  private void addJavaDoc(StringBuilder stringBuilder, DomainClass domainClass) {
    var example =
        domainClass.getExample() != null
            ? "<pre>\nExamples:\n*%s\n</pre>".formatted(domainClass.getExample())
            : "";
    stringBuilder.append("/**\n*%s\n*\n*%s\n*/".formatted(domainClass.getDescription(), example));
  }
}
