package uk.brdr.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import uk.brdr.data.dao.GeoLocationsDao;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.model.location.GeoLocation;
import uk.brdr.model.sighting.GeoSighting;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingForUser;

public class SightingsServiceImpl implements SightingsService {

  private final SightingsDao sightingsDao;
  private final GeoLocationsDao geoLocationsDao;

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
  public List<GeoSighting> getSightingsForUser(int userId) {
    var sightings = sightingsDao.getGeoSightings(userId);
    var sightingGeos = sightings.stream().map(SightingForUser::getGeoId).distinct().collect(toList());
    var geoLocations = geoLocationsDao.getGeos(sightingGeos);
    return groupSightingsByGeo(sightings, geoLocations);
  }

  private List<GeoSighting> groupSightingsByGeo(List<SightingForUser> sightings,
      List<GeoLocation> geoLocations) {
    return geoLocations.stream().map(g -> {
      var species = sightings.stream().filter(s -> s.getGeoId() == g.getId())
          .map(SightingForUser::getSpeciesId).collect(toList());
      return new GeoSighting(g.getName(), g.getGeo(), species);
    }).collect(toList());
  }

  private boolean sightingExists(Sighting sighting) {
    var sightings = sightingsDao.getGeoSightings(sighting.userId);
    var existingSighting =
        sightings.stream()
            .filter(
                currSighting ->
                    currSighting.getGeoId() == sighting.getLocationId()
                        && sighting.getSpecies().contains(currSighting.getSpeciesId()))
            .findFirst();
    return existingSighting.isPresent();
  }
}
