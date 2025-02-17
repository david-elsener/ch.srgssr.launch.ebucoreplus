package ch.srgssr.launch.ebucoreplus.collector;

import ch.srgssr.launch.ebucoreplus.model.*;
import java.util.*;
import java.util.function.Predicate;
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
    processClassHierarchies();
    processProperties();
    postProcessClasses();
    assignDomainModulesToDomainClasses();
    assignRemainingDomainModulesToDomainClasses();
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

  private void processClassHierarchies() {
    for (var domainClass : domainClasses) {
      var superClasses =
          ontology.getSubClassAxiomsForSubClass(domainClass.getOwlClass()).stream()
              .map(OWLSubClassOfAxiom::getSuperClass)
              .filter(Predicate.not(OWLRestriction.class::isInstance))
              .filter(Objects::nonNull)
              .toList();
      if (superClasses.size() == 1) {
        var superClass = superClasses.getFirst().toString().replace("<", "").replace(">", "");
        if (!EXCLUSIONS_POST_PROCESSING.contains(superClass)) {
          domainClass.setSuperClass(findClassByUri(superClass));
        }
      } else if (superClasses.size() > 1) {
        throw new IllegalStateException(
            "found more than 1 direct super class for %s: %s"
                .formatted(domainClass.getUri(), superClasses));
      }
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

  private List<DomainClassReference> collectClassTree(
      DomainClass domainClass, boolean includeInOutReferences) {
    var tree = new ArrayList<DomainClassReference>();
    tree.add(domainClass);
    tree.addAll(collectSuperClasses(domainClass));
    if (includeInOutReferences) {
      tree.addAll(collectInOutReferences(domainClass, new ArrayList<>(), true));
    }
    return tree;
  }

  private List<DomainClassReference> collectClassTree(DomainEnumClass enumClass) {
    var tree = new ArrayList<DomainClassReference>();
    tree.add(enumClass);
    tree.addAll(collectInReferences(enumClass, new ArrayList<>()));
    return tree;
  }

  private List<DomainClassReference> collectSuperClasses(DomainClass domainClass) {
    var superClasses = new ArrayList<DomainClassReference>();
    var currentClass = domainClass;
    while (currentClass.getSuperclass() != null) {
      superClasses.add(currentClass.getSuperclass());
      currentClass = currentClass.getSuperclass();
    }
    return superClasses;
  }

  private List<DomainClassReference> collectInOutReferences(
      DomainClass domainClass, List<String> visitedClasses, boolean includeOutReferences) {
    var inOutReferences = new ArrayList<DomainClassReference>();
    if (visitedClasses.contains(domainClass.getUri())) {
      return inOutReferences;
    }
    inOutReferences.addAll(
        domainClasses.stream()
            .filter(other -> !other.getUri().equals(domainClass.getUri()))
            .filter(other -> referencesClass(other.getProperties(), domainClass))
            .distinct()
            .toList());

    if (includeOutReferences) {
      inOutReferences.addAll(
          domainClass.getProperties().stream()
              .filter(DomainPropertyObject.class::isInstance)
              .map(DomainPropertyObject.class::cast)
              .map(DomainPropertyObject::getDomainClassReference)
              .distinct()
              .toList());
    }

    visitedClasses.add(domainClass.getUri());

    return inOutReferences;
  }

  private List<DomainClassReference> collectInReferences(
      DomainEnumClass enumClass, List<String> visitedClasses) {
    var references = new ArrayList<DomainClassReference>();
    if (visitedClasses.contains(enumClass.getUri())) {
      return references;
    }
    references.addAll(
        domainClasses.stream()
            .filter(other -> !other.getUri().equals(enumClass.getUri()))
            .filter(other -> referencesClass(other.getProperties(), other))
            .distinct()
            .toList());
    visitedClasses.add(enumClass.getUri());

    references.addAll(
        references.stream()
            .filter(DomainClass.class::isInstance)
            .map(DomainClass.class::cast)
            .map(inOutReference -> collectInOutReferences(inOutReference, visitedClasses, false))
            .flatMap(List::stream)
            .distinct()
            .toList());

    return references;
  }

  private boolean referencesClass(List<DomainProperty> properties, DomainClass domainClass) {
    return properties.stream()
        .filter(DomainPropertyObject.class::isInstance)
        .map(DomainPropertyObject.class::cast)
        .anyMatch(property -> matchesReference(property, domainClass.getName()));
  }

  private void assignDomainModulesToDomainClasses() {
    for (var domainClass : domainClasses) {
      if (domainClass.getModule() == null) {
        var module =
            collectClassTree(domainClass, false).stream()
                .map(DomainClassReference::getName)
                .map(this::findModuleByClassName)
                .filter(Objects::nonNull)
                .distinct()
                .findFirst()
                .orElse(null);
        domainClass.setModule(module);
      }
    }
  }

  private void assignRemainingDomainModulesToDomainClasses() {
    for (var domainClass : domainClasses) {
      if (domainClass.getModule() == null) {
        var module =
            collectClassTree(domainClass, true).stream()
                .map(DomainClassReference::getModule)
                .filter(Objects::nonNull)
                .distinct()
                .findFirst()
                .orElse(DomainModule.COMMON);
        domainClass.setModule(module);
      }
    }
  }

  private void assignDomainModulesToEnumClasses() {
    for (var enumClass : domainEnumClasses) {
      var module =
          collectClassTree(enumClass).stream()
              .map(DomainClassReference::getName)
              .map(this::findModuleByClassName)
              .filter(Objects::nonNull)
              .distinct()
              .findFirst()
              .orElse(DomainModule.COMMON);
      enumClass.setModule(module);
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
