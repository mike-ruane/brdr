package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingDetail;
import uk.brdr.model.sighting.UserSighting;

public interface SightingsDao {

  void addSighting(Sighting sighting);

  List<UserSighting> getSightings(int userId);

  List<SightingDetail> getSightings(int geoId, int userId);
}
