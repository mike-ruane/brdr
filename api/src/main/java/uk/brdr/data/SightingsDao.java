package uk.brdr.data;

import java.util.List;
import uk.brdr.model.Sighting;

public interface SightingsDao {

  void addSighting(Sighting sighting);
  List<Sighting> getSightings();

}
