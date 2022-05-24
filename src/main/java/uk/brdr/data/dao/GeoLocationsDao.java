package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.location.GeoLocation;

public interface GeoLocationsDao {

  List<GeoLocation> getAll();

  List<GeoLocation> getGeos(List<Integer> geoIds);
}
