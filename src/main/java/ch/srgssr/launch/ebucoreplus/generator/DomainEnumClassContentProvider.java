package ch.srgssr.launch.ebucoreplus.generator;

import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.provider.EnumContentGeneratorProvider;
import java.util.List;

public class DomainEnumClassContentProvider extends EnumContentGeneratorProvider {

  @Override
  protected ClassElementGenerator getDefaultClassDeclarationGenerator(
      List<ClassElementGenerator> defaultClassAnnotationGenerators) {
    return new EnumClassDeclarationGenerator(getDefaultClassAnnotationGenerators());
  }
}
