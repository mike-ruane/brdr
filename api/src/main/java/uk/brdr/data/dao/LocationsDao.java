package uk.brdr.data.dao;

import java.util.List;
import uk.brdr.model.location.LocationGrouping;
import uk.brdr.model.location.LocationSelect;
import uk.brdr.model.location.LocationType;

public interface LocationsDao {

  List<LocationSelect> getLocationByCounty(int countyId);

  List<LocationSelect> getCounties();

  List<LocationGrouping> getLocationGrouping(LocationType locationType);
}
