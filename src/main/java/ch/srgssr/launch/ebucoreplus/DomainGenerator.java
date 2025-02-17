package ch.srgssr.launch.ebucoreplus;

import ch.srgssr.launch.ebucoreplus.collector.DomainClassCollector;
import ch.srgssr.launch.ebucoreplus.generator.DomainClassGenerator;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@Slf4j
public class DomainGenerator {

  private static final String EBUCOREPLUS_RDF = "/ebucoreplus.rdf";

  public static void main(String[] args) {
    try (var inputStream = DomainGenerator.class.getResourceAsStream(EBUCOREPLUS_RDF)) {
      if (inputStream == null) {
        throw new IllegalStateException("failed to read ebucoreplus ontology");
      }
      var manager = OWLManager.createOWLOntologyManager();
      var ontology = manager.loadOntologyFromOntologyDocument(inputStream);

      var domainClassCollector = new DomainClassCollector(ontology);
      domainClassCollector.collectDomainClasses();
      var domainClasses = domainClassCollector.getDomainClasses();
      var enumClasses = domainClassCollector.getDomainEnumClasses();
      log.info(
          "initialized {} domain classes and {} enum classes",
          domainClasses.size(),
          enumClasses.size());
      new DomainClassGenerator().generateSourceCode(domainClasses, enumClasses);
      log.info("generation of domain classes completed successfully");
    } catch (IOException | OWLOntologyCreationException exception) {
      log.error("failed to read ontology file", exception);
    }
  }
}
