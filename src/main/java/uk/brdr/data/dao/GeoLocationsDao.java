package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.location.Geo;
import uk.brdr.model.location.GeoLocation;

public interface GeoLocationsDao {

  List<GeoLocation> getGeos(List<Integer> geoIds);

  List<Geo> getAllGeoNames();
}
