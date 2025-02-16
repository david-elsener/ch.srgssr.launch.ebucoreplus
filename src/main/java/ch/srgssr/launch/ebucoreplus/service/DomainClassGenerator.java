package ch.srgssr.launch.ebucoreplus.service;

import ch.srgssr.launch.ebucoreplus.model.DomainClass;
import com.github.vladislavsevruk.generator.java.JavaClassGenerator;
import com.github.vladislavsevruk.generator.java.config.JavaClassGeneratorConfig;
import com.github.vladislavsevruk.generator.java.storage.SchemaObjectStorageImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainClassGenerator {

  public void generateSourceCode(Collection<DomainClass> domainClasses) {
    clearTarget();
    var storage = new SchemaObjectStorageImpl();
    domainClasses.forEach(domainClass -> storage.put(domainClass.getUri(), domainClass));
    log.info("prepared {} schema objects to generated java source code for", domainClasses.size());
    new JavaClassGenerator().generateJavaClasses(config(), storage);
    log.info("successfully generated classes");
  }

  private JavaClassGeneratorConfig config() {
    return JavaClassGeneratorConfig.builder()
        .addJacksonAnnotations(false)
        .addEmptyLineBetweenFields(true)
        .useLombokAnnotations(true)
        .pathToTargetFolder("src/main/generated")
        .build();
  }

  private void clearTarget() {
    var targetPath = Path.of("src/main/generated");
    if (Files.exists(targetPath)) {
      try (var files = Files.list(targetPath)) {
        for (var file : files.toList()) {
          Files.delete(file);
        }
        log.info("cleared target folder {}", targetPath);
      } catch (IOException ioException) {
        log.error("failed to clear target folder {}", targetPath);
      }
    }
  }
}
