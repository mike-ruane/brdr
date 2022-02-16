package uk.brdr.data.repositories;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import uk.brdr.data.dao.LocationsDao;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.model.location.LocationGrouping;
import uk.brdr.model.location.LocationType;
import uk.brdr.model.sighting.SightingByLocation;
import uk.brdr.model.sighting.SightingOverview;

public class SightingsOverviewImpl implements SightingsOverview {

  private final SightingsDao sightingsDao;
  private final LocationsDao locationsDao;

  public SightingsOverviewImpl(SightingsDao sightingsDao, LocationsDao locationsDao) {
    this.sightingsDao = sightingsDao;
    this.locationsDao = locationsDao;
  }

  @Override
  public List<SightingByLocation> getSightingsForUserByLocation(
      int userId, LocationType locationType) {
    var sightings = sightingsDao.getSightings(userId);
    var locations = locationsDao.getLocationGrouping(locationType);
    var groupedSightings = groupSightings(sightings, locationType);
    return mapToSightingByLocation(groupedSightings, locations);
  }

  private Map<Integer, List<SightingOverview>> groupSightings(
      List<SightingOverview> sightings, LocationType locationType) {
    switch (locationType) {
      case COUNTIES:
        return sightings.stream().collect(groupingBy(SightingOverview::getCountyId));
      case REGIONS:
        return sightings.stream().collect(groupingBy(SightingOverview::getRegionId));
      case COUNTRIES:
        return sightings.stream().collect(groupingBy(SightingOverview::getCountryId));
      default:
        return sightings.stream().collect(groupingBy(SightingOverview::getLocationId));
    }
  }

  private List<SightingByLocation> mapToSightingByLocation(
      Map<Integer, List<SightingOverview>> groupedSightings, List<LocationGrouping> locations) {
    return groupedSightings.entrySet().stream()
        .map(
            entry -> {
              var locationGrouping =
                  locations.stream()
                      .filter(location -> location.id == entry.getKey())
                      .findFirst()
                      .orElseThrow();
              return new SightingByLocation(
                  locationGrouping.name,
                  locationGrouping.lat,
                  locationGrouping.lon,
                  entry.getValue());
            })
        .collect(Collectors.toList());
  }
}
