package ch.srgssr.launch.ebucoreplus.model;

import ch.srgssr.launch.ebucoreplus.model.value.SimpleJavaType;
import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.CommonJavaSchemaEntity;
import com.github.vladislavsevruk.generator.java.type.predefined.PrimitiveSchemaEntity;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DomainPropertyValue implements DomainProperty {

  private String uri;
  private String name;
  private String description;
  private String valueType;

  @Override
  public SchemaEntity getType() {
    if (valueType.toLowerCase().contains("object")) {
      return CommonJavaSchemaEntity.OBJECT;
    }
    if (valueType.toLowerCase().contains("string") || valueType.toLowerCase().contains("literal")) {
      return CommonJavaSchemaEntity.STRING;
    }
    if (valueType.toLowerCase().contains("boolean")) {
      return PrimitiveSchemaEntity.BOOLEAN;
    }
    if (valueType.toLowerCase().contains("long")) {
      return PrimitiveSchemaEntity.LONG;
    }
    if (valueType.toLowerCase().contains("float")
        || valueType.toLowerCase().contains("double")
        || valueType.toLowerCase().contains("decimal")) {
      return PrimitiveSchemaEntity.DOUBLE;
    }
    if (valueType.toLowerCase().contains("int")) {
      return PrimitiveSchemaEntity.INT;
    }
    if (valueType.toLowerCase().contains("nonnegativeinteger")
        || valueType.toLowerCase().contains("integer")) {
      return CommonJavaSchemaEntity.INTEGER;
    }
    if (valueType.toLowerCase().contains("date")) {
      return new SimpleJavaType(LocalDate.class);
    }
    if (valueType.toLowerCase().contains("time")) {
      return new SimpleJavaType(LocalTime.class);
    }
    if (valueType.toLowerCase().contains("anyuri")) {
      return new SimpleJavaType(URI.class);
    }
    throw new IllegalStateException(
        "unknown value type '%s' of property '%s'".formatted(valueType, uri));
  }
}
