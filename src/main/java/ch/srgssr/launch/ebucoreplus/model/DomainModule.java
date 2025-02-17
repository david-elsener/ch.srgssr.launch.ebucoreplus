package ch.srgssr.launch.ebucoreplus.model;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DomainModule {
  PLANNING_CAMPAIGNS(
      "ch.srgssr.launch.planning.campaigns", "Campaign", "CampaignPackage", "Audience"),
  PLANNING_PUBLICATION_PLAN("ch.srgssr.launch.planning.publicationplan", "PublicationPlan"),
  PLANNING_LEGAL_ASSETS("ch.srgssr.launch.legal.assets", "Asset"),
  PLANNING_LEGAL_CONTRACTS("ch.srgssr.launch.legal.contracts", "Contract"),
  PLANNING_LEGAL_RIGHTS("ch.srgssr.launch.legal.rights", "Rights"),
  EDITORIAL("ch.srgssr.launch.editorial", "Editorial", "EditorialGroup", "EditorialWork"),
  PRODUCTION_MEDIA_RESOURCES("ch.srgssr.launch.production", "MediaResource"),
  PRODUCTION_JOBS("ch.srgssr.launch.production.jobs", "ProductionJob"),
  PUBLICATION("ch.srgssr.launch.publication", "PublicationPlatform", "PublicationEvent"),
  CONSUMPTION("ch.srgssr.launch.consumption", "Consumer"),
  COMMON("ch.srgssr.launch.common");

  private final String packageName;
  private final List<String> rootEntities;

  DomainModule(String packageName, String... rootEntities) {
    this.packageName = packageName;
    this.rootEntities = List.of(rootEntities);
  }
}
