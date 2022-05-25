package uk.brdr.services;

import java.util.List;
import uk.brdr.model.sighting.GeoSighting;
import uk.brdr.model.sighting.Sighting;

public interface SightingsService {

  void addSighting(Sighting sighting);

  List<GeoSighting> getSightingsForUser(int userId);


}
