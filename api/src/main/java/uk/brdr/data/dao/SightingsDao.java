package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.Sighting;
import uk.brdr.model.SightingOverview;

public interface SightingsDao {

  void addSighting(Sighting sighting);
  List<SightingOverview> getSightings(int userId);

}
