package uk.brdr.data.dao;

import java.sql.Date;
import java.util.List;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.UserSighting;

public interface SightingsDao {

  void addSighting(Sighting sighting);

  List<UserSighting> getSightings(int userId);

  List<UserSighting> getSightingsByDate(int userId, Date date);

  List<Species> getSightings(int geoId, int userId);
}
