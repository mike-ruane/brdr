package uk.brdr.data.repositories;

import java.util.List;
import uk.brdr.model.location.LocationType;
import uk.brdr.model.sighting.SightingByLocation;

public interface SightingsOverview {
  List<SightingByLocation> getSightingsForUserByLocation(int userId, LocationType locationType);
}
