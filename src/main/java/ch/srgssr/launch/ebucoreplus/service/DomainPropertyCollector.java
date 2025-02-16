package ch.srgssr.launch.ebucoreplus.service;

import ch.srgssr.launch.ebucoreplus.model.DomainClass;
import ch.srgssr.launch.ebucoreplus.model.DomainProperty;
import ch.srgssr.launch.ebucoreplus.model.DomainPropertyObject;
import ch.srgssr.launch.ebucoreplus.model.DomainPropertyValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.*;

@Slf4j
@RequiredArgsConstructor
public class DomainPropertyCollector {

  private final OWLOntology ontology;
  private final Collection<DomainClass> domainClasses;
  private final List<DomainProperty> domainProperties = new ArrayList<>();

  public List<DomainProperty> collectDomainProperties() {
    processSimpleProperties();
    processObjectProperties();
    return domainProperties;
  }

  public void processSimpleProperties() {
    var dataProperties = ontology.getDataPropertiesInSignature();
    for (var dataProperty : dataProperties) {
      var property =
          DomainPropertyValue.builder()
              .uri(dataProperty.getIRI().getIRIString())
              .name(dataProperty.getIRI().getShortForm())
              .description(getDescriptionEn(dataProperty.getIRI()))
              .valueType(getValueType(dataProperty))
              .build();
      domainProperties.add(property);
      log.info("initialized simple value property {}", property);
    }
  }

  public void processObjectProperties() {
    var objectProperties = ontology.getObjectPropertiesInSignature();
    for (var objectProperty : objectProperties) {
      var uri = objectProperty.getIRI();
      if (uri.getIRIString().startsWith("http://www.w3.org")) {
        var property =
            DomainPropertyValue.builder()
                .uri(uri.getIRIString())
                .name(uri.getShortForm())
                .description(getDescriptionEn(uri))
                .valueType(uri.getIRIString())
                .build();
        domainProperties.add(property);
        log.info("initialized property {} that explicitly is mapped to simple value", property);
      } else {
        var property =
            DomainPropertyObject.builder()
                .uri(uri.getIRIString())
                .name(uri.getShortForm())
                .description(getDescriptionEn(uri))
                .domainClass(findClassByUri(getObjectType(objectProperty)))
                .build();
        domainProperties.add(property);
        log.info("initialized object property {}", property);
      }
    }
  }

  private String getValueType(OWLDataProperty dataProperty) {
    var ranges =
        ontology.getDataPropertyRangeAxioms(dataProperty).stream()
            .map(OWLDataPropertyRangeAxiom::getRange)
            .map(Object::toString)
            .toList();
    return ranges.size() == 1 ? ranges.getFirst() : null;
  }

  private String getObjectType(OWLObjectProperty objectProperty) {
    var ranges =
        ontology.getObjectPropertyRangeAxioms(objectProperty).stream()
            .map(OWLObjectPropertyRangeAxiom::getRange)
            .map(Object::toString)
            .map(range -> range.replace(">", "").replace("<", ""))
            .toList();
    return ranges.size() == 1 ? ranges.getFirst() : null;
  }

  private String getDescriptionEn(IRI iri) {
    return ontology.getAnnotationAssertionAxioms(iri).stream()
        .filter(this::isDescriptionType)
        .filter(annotation -> annotation.getValue().isLiteral())
        .filter(this::isEnglish)
        .findFirst()
        .map(OWLAnnotationAssertionAxiom::getValue)
        .flatMap(OWLAnnotationValue::asLiteral)
        .map(OWLLiteral::getLiteral)
        .orElse(null);
  }

  private boolean isDescriptionType(OWLAnnotationAssertionAxiom annotation) {
    return annotation.getProperty().getIRI().getShortForm().equalsIgnoreCase("description");
  }

  private boolean isEnglish(OWLAnnotationAssertionAxiom annotation) {
    return "en".equals(annotation.getValue().asLiteral().map(OWLLiteral::getLang).orElse(null));
  }

  private DomainClass findClassByUri(String uri) {
    return domainClasses.stream()
        .filter(domainClass -> domainClass.getUri().equals(uri))
        .findFirst()
        .orElse(null);
  }
}
