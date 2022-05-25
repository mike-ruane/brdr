package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingForUser;

public interface SightingsDao {

  void addSighting(Sighting sighting);

  List<SightingForUser> getGeoSightings(int userId);
}
