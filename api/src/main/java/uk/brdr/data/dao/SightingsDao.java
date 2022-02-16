package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingOverview;

public interface SightingsDao {

  void addSighting(Sighting sighting);

  List<SightingOverview> getSightings(int userId);
}
