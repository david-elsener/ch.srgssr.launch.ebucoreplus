package ch.srgssr.launch.ebucoreplus.generator;

import ch.srgssr.launch.ebucoreplus.model.DomainClass;
import ch.srgssr.launch.ebucoreplus.model.DomainEnumClass;
import ch.srgssr.launch.ebucoreplus.model.DomainModule;
import com.github.vladislavsevruk.generator.java.JavaClassGenerator;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.context.ClassGenerationContextManager;
import com.github.vladislavsevruk.generator.java.provider.ClassContentGeneratorProvider;
import com.github.vladislavsevruk.generator.java.provider.EnumContentGeneratorProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainClassGenerator {

  public void generateSourceCode(
      Collection<DomainClass> domainClasses, Collection<DomainEnumClass> enumClasses) {
    registerCustomClassContentProviders();
    var targetPath = Path.of("src/main/generated");
    if (Files.exists(targetPath)) {
      deleteFiles(targetPath);
    }
    for (var domainClass : domainClasses) {
      new JavaClassGenerator().generateJavaClass(config(domainClass.getModule()), domainClass);
      log.info("generated Java domain class {}", domainClass);
    }
    for (var enumClass : enumClasses) {
      new JavaClassGenerator().generateJavaClass(config(enumClass.getModule()), enumClass);
      log.info("generated Java enum class {}", enumClass);
    }
  }

  private static void registerCustomClassContentProviders() {
    ClassGenerationContextManager.getContext()
        .getClassContentGeneratorProviderStorage()
        .addBefore(new DomainClassContentProvider(), ClassContentGeneratorProvider.class);
    ClassGenerationContextManager.getContext()
        .getClassContentGeneratorProviderStorage()
        .addBefore(new DomainEnumClassContentProvider(), EnumContentGeneratorProvider.class);
  }

  private JavaClassGeneratorConfig config(DomainModule module) {
    return JavaClassGeneratorConfig.builder()
        .addJacksonAnnotations(false)
        .addEmptyLineBetweenFields(true)
        .useLombokAnnotations(true)
        .pathToTargetFolder("src/main/generated/" + module.getPackageName().replace(".", "/"))
        .build();
  }

  private void deleteFiles(Path target) {
    try {
      if (Files.isDirectory(target)) {
        try (var files = Files.list(target)) {
          for (var file : files.toList()) {
            deleteFiles(file);
          }
        }
      }
      Files.delete(target);
    } catch (IOException ioException) {
      throw new IllegalStateException("failed to delete %s".formatted(target), ioException);
    }
  }
}
