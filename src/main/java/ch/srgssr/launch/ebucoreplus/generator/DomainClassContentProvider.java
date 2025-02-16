package ch.srgssr.launch.ebucoreplus.generator;

import com.github.vladislavsevruk.generator.java.generator.ClassElementCollectionGenerator;
import com.github.vladislavsevruk.generator.java.generator.ClassElementGenerator;
import com.github.vladislavsevruk.generator.java.provider.ClassContentGeneratorProvider;
import java.util.ArrayList;
import java.util.List;

public class DomainClassContentProvider extends ClassContentGeneratorProvider {

  @Override
  protected ClassElementGenerator getDefaultClassDeclarationGenerator(
      List<ClassElementGenerator> defaultClassAnnotationGenerators) {
    return new DomainClassDeclarationGenerator(getDefaultClassAnnotationGenerators());
  }

  @Override
  protected List<ClassElementCollectionGenerator> getDefaultFieldGenerators() {
    List<ClassElementCollectionGenerator> defaultFieldGenerators = new ArrayList<>();
    defaultFieldGenerators.add(
        new DomainClassFieldGenerator(getDefaultFieldAnnotationGenerators()));
    return defaultFieldGenerators;
  }
}
