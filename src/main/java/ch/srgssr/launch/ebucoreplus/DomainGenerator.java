package ch.srgssr.launch.ebucoreplus;

import ch.srgssr.launch.ebucoreplus.service.DomainGeneratorService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

@Slf4j
public class DomainGenerator {

  private static final String EBUCOREPLUS_RDF = "/ebucoreplus.rdf";

  public static void main(String[] args) {
    try (var inputStream = DomainGenerator.class.getResourceAsStream(EBUCOREPLUS_RDF)) {
      var model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
      model.read(inputStream, null);

      var domainClasses = new DomainGeneratorService(model).collectDomainClasses();
      log.info("initialized {} domain classes", domainClasses.size());
    } catch (IOException exception) {
      log.error("failed to read ontology file", exception);
    }
  }
}
