package ch.srgssr.launch.ebucoreplus.service;

import ch.srgssr.launch.ebucoreplus.generator.DomainClassContentProvider;
import ch.srgssr.launch.ebucoreplus.model.DomainClass;
import ch.srgssr.launch.ebucoreplus.model.DomainModule;
import com.github.vladislavsevruk.generator.java.JavaClassGenerator;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.context.ClassGenerationContextManager;
import com.github.vladislavsevruk.generator.java.provider.ClassContentGeneratorProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainClassGenerator {

  public void generateSourceCode(Collection<DomainClass> domainClasses) {
    registerCustomClassContentProvider();
    var targetPath = Path.of("src/main/generated");
    if (Files.exists(targetPath)) {
      deleteFiles(targetPath);
    }
    for (var domainClass : domainClasses) {
      new JavaClassGenerator().generateJavaClass(config(domainClass.getModule()), domainClass);
      log.info("generated Java class {}", domainClass);
    }
  }

  private static void registerCustomClassContentProvider() {
    ClassGenerationContextManager.getContext()
        .getClassContentGeneratorProviderStorage()
        .addBefore(new DomainClassContentProvider(), ClassContentGeneratorProvider.class);
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
