package uk.brdr.services;

import java.util.List;
import uk.brdr.model.location.LocationType;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingByLocation;

public interface SightingsService {

  void addSighting(Sighting sighting);

  List<SightingByLocation> getSightingsForUserByLocation(int userId, LocationType locationType);
}
