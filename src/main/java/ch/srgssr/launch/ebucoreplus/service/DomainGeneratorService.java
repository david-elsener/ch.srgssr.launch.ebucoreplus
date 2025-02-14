package ch.srgssr.launch.ebucoreplus.service;

import ch.srgssr.launch.ebucoreplus.model.DomainClass;
import ch.srgssr.launch.ebucoreplus.model.DomainProperty;
import ch.srgssr.launch.ebucoreplus.model.DomainPropertyObject;
import ch.srgssr.launch.ebucoreplus.model.DomainPropertyValue;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;

@Slf4j
@RequiredArgsConstructor
public class DomainGeneratorService {

  private final OntModel rootModel;
  private final Set<DomainClass> domainClasses = new HashSet<>();

  public Set<DomainClass> collectDomainClasses() {
    initializeDomainClasses(this.rootModel);
    initializeProperties(this.rootModel);
    return domainClasses;
  }

  private void initializeDomainClasses(OntModel model) {
    for (OntClass ontClass : model.listClasses().toList()) {
      if (!ontClass.isAnon()) {
        var domainClass = initializeDomainClass(ontClass);
        log.info("initialized class {}", ontClass.getURI());
        inintializeSubclasses(ontClass, domainClass);
      } else {
        log.info("skipping class {}", ontClass.getURI());
      }
    }
    model.listSubModels().forEach(this::initializeDomainClasses);
  }

  private DomainClass initializeDomainClass(OntClass ontClass) {
    var domainClass =
        DomainClass.builder()
            .ontUri(ontClass.getURI())
            .name(ontClass.getLocalName())
            .description(ontClass.getComment("en"))
            .build();
    domainClasses.add(domainClass);
    return domainClass;
  }

  private void inintializeSubclasses(OntClass ontClass, DomainClass domainClass) {
    ontClass
        .listSubClasses()
        .forEach(
            subclass -> {
              var domainSubclass = initializeDomainClass(ontClass);
              log.info("initialized subclass {} of class {}", subclass.getURI(), ontClass.getURI());
              domainClass.getSubclasses().add(domainSubclass);
              inintializeSubclasses(subclass, domainSubclass);
            });
  }

  private void initializeProperties(OntModel model) {
    model
        .listOntProperties()
        .forEach(
            ontProperty -> {
              if (ontProperty.getDomain() == null) {
                log.warn("ignoring property {}, no domain set", ontProperty.getURI());
                return;
              }
              if (ontProperty.getRange() == null) {
                log.warn("ignoring property {}, no range set", ontProperty.getURI());
                return;
              }
              var domainClass = findClassByUri(ontProperty.getDomain().getURI());
              if (isSimpleValueProperty(ontProperty)) {
                var property = initializeDomainPropertyValue(ontProperty);
                domainClass.getProperties().add(property);
                log.info("initialized simple value property {}", property);
                return;
              }
              if (isObjectProperty(ontProperty)) {
                var property = initializeDomainPropertyObject(ontProperty);
                domainClass.getProperties().add(property);
                log.info("initialized object property {}", property);
                return;
              }
            });
    model.listSubModels().forEach(this::initializeProperties);
  }

  private boolean isSimpleValueProperty(OntProperty ontProperty) {
    return ontProperty.getRange().getURI().startsWith("http://www.w3.org/2001/XMLSchema#");
  }

  private boolean isObjectProperty(OntProperty ontProperty) {
    return ontProperty
        .getRange()
        .getURI()
        .startsWith("http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#");
  }

  private DomainProperty initializeDomainPropertyValue(OntProperty property) {
    return DomainPropertyValue.builder()
        .ontUri(property.getURI())
        .name(property.getLocalName())
        .description(property.getComment("en"))
        .type(property.getRange().getLocalName())
        .build();
  }

  private DomainProperty initializeDomainPropertyObject(OntProperty property) {
    return DomainPropertyObject.builder()
        .ontUri(property.getURI())
        .name(property.getLocalName())
        .description(property.getComment("en"))
        .domainClass(findClassByUri(property.getRange().getURI()))
        .build();
  }

  private DomainClass findClassByUri(String uri) {
    return domainClasses.stream()
        .filter(domainClass -> domainClass.getOntUri().equals(uri))
        .findFirst()
        .orElseThrow();
  }
}
