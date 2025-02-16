package ch.srgssr.launch.ebucoreplus.service;

import ch.srgssr.launch.ebucoreplus.model.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.*;

@Slf4j
@RequiredArgsConstructor
public class DomainClassCollector {

  private static final List<String> EXCLUSIONS =
      List.of("http://www.w3.org/2006/time#DateTimeDescription");

  private final OWLOntology ontology;
  private final Set<DomainClass> domainClasses = new HashSet<>();
  private final Set<DomainProperty> domainProperties = new HashSet<>();

  public Set<DomainClass> collectDomainClasses() {
    processClasses();
    processProperties();
    return domainClasses;
  }

  private void processClasses() {
    for (var owlClass : ontology.getClassesInSignature()) {
      if (EXCLUSIONS.contains(owlClass.getIRI().getIRIString())) {
        log.info("skipping excluded class {}", owlClass.getIRI().getIRIString());
        continue;
      }
      var domainClass = initializeDomainClass(owlClass);
      log.info("initialized class {}", owlClass.getIRI().getIRIString());
      domainClasses.add(domainClass);
    }
  }

  private void processProperties() {
    domainProperties.addAll(
        new DomainPropertyCollector(ontology, domainClasses).collectDomainProperties());
    for (var domainClass : domainClasses) {
      initializeProperties(domainClass);
      log.info("initialized properties for class {}", domainClass.getUri());
    }
  }

  private DomainClass initializeDomainClass(OWLClass owlClass) {

    var domainClass =
        DomainClass.builder()
            .owlClass(owlClass)
            .uri(owlClass.getIRI().getIRIString())
            .name(owlClass.getIRI().getShortForm())
            .description(getDescriptionEn(owlClass))
            .example(getExampleEn(owlClass))
            .module(DomainModule.PLANNING_CAMPAIGNS) // FIXME
            .build();
    domainClasses.add(domainClass);
    return domainClass;
  }

  private String getDescriptionEn(OWLClass owlClass) {
    return getAnnotation(owlClass, "definition");
  }

  private String getExampleEn(OWLClass owlClass) {
    return getAnnotation(owlClass, "example");
  }

  private String getAnnotation(OWLClass owlClass, String type) {
    return ontology.getAnnotationAssertionAxioms(owlClass.getIRI()).stream()
        .filter(annotation -> isAnnotationType(annotation, type))
        .filter(annotation -> annotation.getValue().isLiteral())
        .filter(this::isEnglish)
        .findFirst()
        .map(OWLAnnotationAssertionAxiom::getValue)
        .flatMap(OWLAnnotationValue::asLiteral)
        .map(OWLLiteral::getLiteral)
        .orElse(null);
  }

  private boolean isAnnotationType(OWLAnnotationAssertionAxiom annotation, String type) {
    return annotation.getProperty().getIRI().getShortForm().equalsIgnoreCase(type);
  }

  private boolean isEnglish(OWLAnnotationAssertionAxiom annotation) {
    return "en".equals(annotation.getValue().asLiteral().map(OWLLiteral::getLang).orElse(null));
  }

  private void initializeProperties(DomainClass domainClass) {
    ontology.getSubClassAxiomsForSubClass(domainClass.getOwlClass()).stream()
        .map(OWLSubClassOfAxiom::getSuperClass)
        .filter(OWLRestriction.class::isInstance)
        .map(OWLRestriction.class::cast)
        .forEach(restriction -> initializeProperty(domainClass, restriction));
  }

  private void initializeProperty(DomainClass domainClass, OWLRestriction restriction) {
    var propertyUri = getPropertyUri(restriction);
    var property =
        domainProperties.stream()
            .filter(domainProperty -> domainProperty.getUri().equals(propertyUri))
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalStateException("could not find property %s".formatted(propertyUri)));
    try {
      if (property instanceof DomainPropertyValue valueProperty) {
        var valueType =
            Optional.ofNullable(valueProperty.getValueType())
                .orElseGet(() -> getValueType(restriction));
        var clone = valueProperty.toBuilder().valueType(valueType).build();
        domainClass.getProperties().add(clone);
      } else if (property instanceof DomainPropertyObject objectProperty) {
        var valueType =
            Optional.ofNullable(objectProperty.getDomainClass())
                .orElseGet(() -> findClassByUri(getValueType(restriction)));
        var clone = objectProperty.toBuilder().domainClass(valueType).build();
        domainClass.getProperties().add(clone);
      }
    } catch (Exception exception) {
      throw new IllegalStateException(
          "failed to initialize property %s: %s".formatted(propertyUri, exception.getMessage()),
          exception);
    }
  }

  private String getPropertyUri(OWLRestriction restriction) {
    if (restriction instanceof OWLObjectRestriction objectRestriction) {
      return objectRestriction.getProperty().asOWLObjectProperty().getIRI().getIRIString();
    } else if (restriction instanceof OWLDataRestriction dataRestriction) {
      return dataRestriction.getProperty().asOWLDataProperty().getIRI().getIRIString();
    }
    throw new IllegalStateException("unsupported restriction type");
  }

  private String getValueType(OWLRestriction restriction) {
    if (restriction instanceof OWLQuantifiedObjectRestriction quantifiedObjectRestriction) {
      return quantifiedObjectRestriction.getFiller().toString().replace("<", "").replace(">", "");
    } else if (restriction instanceof OWLQuantifiedDataRestriction quantifiedDataRestriction) {
      return quantifiedDataRestriction.getFiller().toString();
    } else if (restriction instanceof OWLHasValueRestriction<?> hasValueRestriction) {
      return hasValueRestriction.getFiller().toString().replace("<", "").replace(">", "");
    }
    throw new IllegalStateException(
        "unknown restriction type %s for property %s"
            .formatted(restriction.getClass(), restriction.getProperty().toString()));
  }

  private DomainClass findClassByUri(String uri) {
    return domainClasses.stream()
        .filter(domainClass -> domainClass.getUri().equals(uri))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("could not find domain class for " + uri));
  }
}
