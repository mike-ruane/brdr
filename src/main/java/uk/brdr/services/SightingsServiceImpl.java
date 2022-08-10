package uk.brdr.services;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import uk.brdr.data.dao.GeoLocationsDao;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.model.Species;
import uk.brdr.model.location.GeometryLocation;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingDetail;
import uk.brdr.model.sighting.SightingsByGeometry;
import uk.brdr.model.sighting.UserSighting;

public class SightingsServiceImpl implements SightingsService {

  private final SightingsDao sightingsDao;
  private final GeoLocationsDao geoLocationsDao;

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public SightingsServiceImpl(SightingsDao sightingsDao, GeoLocationsDao geoLocationsDao) {
    this.sightingsDao = sightingsDao;
    this.geoLocationsDao = geoLocationsDao;
  }

  @Override
  public void addSighting(Sighting sighting) {
    if (sightingExists(sighting)) {
      throw new IllegalStateException("sighting already exists");
    }
    sightingsDao.addSighting(sighting);
  }

  @Override
  public List<SightingsByGeometry> getSightings(int userId) {
    var sightings = sightingsDao.getSightings(userId);
    if (sightings.isEmpty()) {
      return List.of();
    }
    var geoIds = sightings.stream().map(UserSighting::getGeoId).distinct().collect(toList());
    var geoLocations = geoLocationsDao.getGeometries(geoIds);
    return groupSightingsByGeometry(sightings, geoLocations);
  }

  @Override
  public Map<String, List<Species>> getSightings(int geoId, int userId) {
    var sightings = sightingsDao.getSightings(geoId, userId);
    return sightings.stream()
        .collect(
            groupingBy(
                sd -> DATE_FORMAT.format(sd.getDate()),
                Collectors.mapping(SightingDetail::getSpecies, toList())));
  }

  @Override
  public Map<String, List<Species>> getSightingsByOrder(int geoId, int userId) {
    var sightings = sightingsDao.getSightings(geoId, userId);
    return sightings.stream()
        .map(SightingDetail::getSpecies)
        .distinct()
        .collect(groupingBy(Species::getFamilyOrder));
  }

  private List<SightingsByGeometry> groupSightingsByGeometry(
      List<UserSighting> sightings, List<GeometryLocation> geoLocations) {
    return geoLocations.stream()
        .map(
            g -> {
              var species =
                  sightings.stream()
                      .filter(s -> s.getGeoId() == g.getId())
                      .map(UserSighting::getSpeciesId)
                      .collect(toList());
              return new SightingsByGeometry(
                  g.getName(), g.getId(), g.getpGgeometry().getGeometry(), species);
            })
        .collect(toList());
  }

  private boolean sightingExists(Sighting sighting) {
    var sightings = sightingsDao.getSightings(sighting.userId);
    var existingSighting =
        sightings.stream()
            .filter(
                currSighting ->
                    currSighting.getGeoId() == sighting.getGeoId()
                        && sighting.getSpecies().contains(currSighting.getSpeciesId()))
            .findFirst();
    return existingSighting.isPresent();
  }
}
