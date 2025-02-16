package ch.srgssr.launch.ebucoreplus.model.value;

import com.github.vladislavsevruk.generator.java.type.SchemaEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleJavaType implements SchemaEntity {

  private final Class<?> clazz;

  @Override
  public String getPackage() {
    return clazz.getPackageName();
  }

  @Override
  public String getName() {
    return clazz.getName();
  }
}
