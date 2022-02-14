package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.location.Location;
import uk.brdr.model.location.LocationGrouping;
import uk.brdr.model.location.LocationType;

public interface LocationsDao {

  List<Location> getLocations();
  List<LocationGrouping> getLocationGrouping(LocationType locationType);

}
