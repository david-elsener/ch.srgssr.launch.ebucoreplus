package ch.srgssr.launch.ebucoreplus.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DomainModule {
  PLANNING_CAMPAIGNS("ch.srgssr.launch.planning.campaigns"),
  PLANNING_PUBLICATION_PLAN("ch.srgssr.launch.planning.publicationplan"),
  PLANNING_LEGAL_ASSETS("ch.srgssr.launch.legal.assets"),
  PLANNING_LEGAL_CONTRACTS("ch.srgssr.launch.legal.contracts"),
  PLANNING_LEGAL_RIGHTS("ch.srgssr.launch.legal.rights"),
  EDITORIAL("ch.srgssr.launch.editorial"),
  PRODUCTION_MEDIA_RESOURCES("ch.srgssr.launch.production.mediaresources"),
  PRODUCTION_JOBS("ch.srgssr.launch.production.jobs"),
  PUBLICATION_PLATFORMS("ch.srgssr.launch.publication.platforms"),
  PUBLICATION_EVENTS("ch.srgssr.launch.publication.events"),
  CONSUMPTION("ch.srgssr.launch.consumption");

  private final String packageName;
}
