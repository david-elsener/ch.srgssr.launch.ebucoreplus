package ch.srgssr.launch.ebucoreplus;

import ch.srgssr.launch.ebucoreplus.service.DomainClassCollector;
import ch.srgssr.launch.ebucoreplus.service.DomainClassGenerator;
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

      var domainClasses = new DomainClassCollector(ontology).collectDomainClasses();
      log.info("initialized {} domain classes", domainClasses.size());
      new DomainClassGenerator().generateSourceCode(domainClasses);
      log.info("generation of domain classes completed successfully");
    } catch (IOException | OWLOntologyCreationException exception) {
      log.error("failed to read ontology file", exception);
    }
  }
}
