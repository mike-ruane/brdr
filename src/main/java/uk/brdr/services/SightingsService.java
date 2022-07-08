package uk.brdr.services;

import java.util.List;
import java.util.Map;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingsByGeometry;

public interface SightingsService {

  void addSighting(Sighting sighting);

  List<SightingsByGeometry> getSightings(int userId);

  Map<String, List<Species>> getSightings(int geoId, int userId);

  Map<String, List<Species>> getSightingsByGenus(int geoId, int userId);
}
