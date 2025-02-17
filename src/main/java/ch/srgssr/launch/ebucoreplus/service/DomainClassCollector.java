package ch.srgssr.launch.ebucoreplus.service;

import ch.srgssr.launch.ebucoreplus.model.*;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.*;

@Slf4j
@RequiredArgsConstructor
public class DomainClassCollector {

  private static final List<String> EXCLUSIONS_OWL_PROCESSING =
      List.of("http://www.w3.org/2006/time#DateTimeDescription");
  private static final List<String> EXCLUSIONS_POST_PROCESSING =
      List.of("http://www.w3.org/2004/02/skos/core#Concept");

  private final OWLOntology ontology;
  @Getter private final Set<DomainClass> domainClasses = new HashSet<>();
  @Getter private final Set<DomainEnumClass> domainEnumClasses = new HashSet<>();
  private final Set<DomainProperty> domainProperties = new HashSet<>();

  public void collectDomainClasses() {
    processClasses();
    processProperties();
    postProcessClasses();
    assignDomainModulesToDomainClasses();
    assignDomainModulesToEnumClasses();
  }

  private void processClasses() {
    for (var owlClass : ontology.getClassesInSignature()) {
      if (EXCLUSIONS_OWL_PROCESSING.contains(owlClass.getIRI().getIRIString())) {
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

  private void postProcessClasses() {
    var sanitizedDomainClasses = new ArrayList<DomainClass>();
    for (var domainClass : domainClasses) {
      if (EXCLUSIONS_POST_PROCESSING.contains(domainClass.getUri())) {
        log.info("postprocessing removed exclusion class {}", domainClass.getUri());
      } else if (domainClass.getName().contains("_Type")) {
        var enumClass =
            DomainEnumClass.builder()
                .uri(domainClass.getUri())
                .owlClass(domainClass.getOwlClass())
                .name(domainClass.getName().replace("_", ""))
                .module(domainClass.getModule())
                .description(domainClass.getDescription())
                .build();
        domainEnumClasses.add(enumClass);
        updateClassReferences(domainClass, enumClass);
      } else {
        sanitizedDomainClasses.add(domainClass);
      }
      updateClassReferencesToConcept(domainClass);
    }
    domainClasses.clear();
    domainClasses.addAll(sanitizedDomainClasses);
  }

  private void assignDomainModulesToDomainClasses() {
    for (var domainClass : domainClasses) {
      if (findModuleByClassName(domainClass.getName()) != null) {
        domainClass.setModule(findModuleByClassName(domainClass.getName()));
      } else if (findModuleByObjectProperty(domainClass) != null) {
        domainClass.setModule(findModuleByObjectProperty(domainClass));
      } else {
        Optional.ofNullable(findReferencingClass(domainClass.getName()))
            .map(this::findModuleByClassName)
            .ifPresent(domainClass::setModule);
      }
    }
  }

  private void assignDomainModulesToEnumClasses() {
    for (var enumClass : domainEnumClasses) {
      var module = findModuleByClassName(enumClass.getName());
      if (module != null) {
        enumClass.setModule(module);
      } else {

        Optional.ofNullable(findReferencingClass(enumClass.getName()))
            .map(this::findModuleByClassName)
            .ifPresent(enumClass::setModule);
      }
    }
  }

  private DomainModule findModuleByClassName(String className) {
    for (var module : DomainModule.values()) {
      if (module.getRootEntities() != null && module.getRootEntities().contains(className)) {
        return module;
      }
    }
    return null;
  }

  private DomainModule findModuleByObjectProperty(DomainClass domainClass) {
    return findModuleByObjectProperty(domainClass, new ArrayList<>());
  }

  private DomainModule findModuleByObjectProperty(
      DomainClass domainClass, List<String> visitedClasses) {
    log.info("findModuleByObjectProperty for {}", domainClass.getName());
    visitedClasses.add(domainClass.getName());
    var objectProperties =
        domainClass.getProperties().stream()
            .filter(DomainPropertyObject.class::isInstance)
            .map(DomainPropertyObject.class::cast)
            .toList();
    var module =
        objectProperties.stream()
            .map(DomainPropertyObject::getDomainClassReference)
            .map(DomainClassReference::getName)
            .map(this::findModuleByClassName)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    if (module != null) {
      return module;
    }
    for (var objectProperty : objectProperties) {
      var referencedClass = findClassByUri(objectProperty.getDomainClassReference().getUri());
      if (referencedClass.getName().equals(domainClass.getName())) {
        continue; // skip self-reference
      }
      if (visitedClasses.contains(referencedClass.getName())) {
        continue; // already checked, probably bidirectional
      }
      log.info("resolving transitive references for {}", referencedClass.getName());
      visitedClasses.add(referencedClass.getName());
      var transitiveModule = findModuleByObjectProperty(referencedClass, visitedClasses);
      if (transitiveModule != null) {
        return transitiveModule;
      }
    }
    return null;
  }

  private String findReferencingClass(String referencedClassName) {
    for (var domainClass : domainClasses) {
      var referencesClass =
          domainClass.getProperties().stream()
              .filter(DomainPropertyObject.class::isInstance)
              .map(DomainPropertyObject.class::cast)
              .anyMatch(property -> matchesReference(property, referencedClassName));
      if (referencesClass) {
        return domainClass.getName();
      }
    }
    return null;
  }

  private boolean matchesReference(DomainPropertyObject domainPropertyObject, String className) {
    return domainPropertyObject.getDomainClassReference().getName().equals(className);
  }

  private void updateClassReferences(DomainClass oldDomainClass, DomainEnumClass newEnumClass) {
    var uri = oldDomainClass.getUri();
    domainClasses.stream()
        .map(DomainClass::getProperties)
        .flatMap(List::stream)
        .filter(DomainPropertyObject.class::isInstance)
        .map(DomainPropertyObject.class::cast)
        .filter(property -> uri.equals(property.getDomainClassReference().getUri()))
        .forEach(property -> property.setDomainClassReference(newEnumClass));
  }

  private void updateClassReferencesToConcept(DomainClass domainClass) {
    var uri = "http://www.w3.org/2004/02/skos/core#Concept";
    var conceptReferences =
        domainClass.getProperties().stream()
            .filter(DomainPropertyObject.class::isInstance)
            .map(DomainPropertyObject.class::cast)
            .filter(property -> uri.equals(property.getDomainClassReference().getUri()))
            .toList();
    if (!conceptReferences.isEmpty()) {
      for (var concept : conceptReferences) {
        if (!concept.getName().equals("objectType")) {
          domainClass
              .getProperties()
              .add(
                  DomainPropertyValue.builder()
                      .valueType("object")
                      .uri(concept.getUri())
                      .name(concept.getName())
                      .description(concept.getDescription())
                      .build());
        }
        domainClass.getProperties().remove(concept);
      }
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
            .module(DomainModule.COMMON)
            .build();
    domainClasses.add(domainClass);
    return domainClass;
  }

  private String getDescriptionEn(OWLClass owlClass) {
    var definition = getAnnotation(owlClass, "definition");
    return definition != null ? definition : getAnnotation(owlClass, "description");
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
            Optional.ofNullable(objectProperty.getDomainClassReference())
                .orElseGet(() -> findClassByUri(getValueType(restriction)));
        var clone = objectProperty.toBuilder().domainClassReference(valueType).build();
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
