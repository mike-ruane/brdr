package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.location.Geo;
import uk.brdr.model.location.GeometryLocation;

public interface GeoLocationsDao {

  List<GeometryLocation> getGeometries(List<Integer> geoIds);

  List<Geo> getAllGeoNames();
}
