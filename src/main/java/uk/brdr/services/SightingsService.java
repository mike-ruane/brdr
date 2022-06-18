package uk.brdr.services;

import java.util.List;
import java.util.Map;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.SightingsByGeo;
import uk.brdr.model.sighting.Sighting;

public interface SightingsService {

  void addSighting(Sighting sighting);

  List<SightingsByGeo> getSightings(int userId);

  Map<String, List<Species>> getSightings(int geoId, int userId);
}
