package uk.brdr.data.daoimpl;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import uk.brdr.data.dao.LocationsDao;
import uk.brdr.data.mappers.LocationGroupingsRowMapper;
import uk.brdr.data.mappers.LocationsRowMapper;
import uk.brdr.model.location.Location;
import uk.brdr.model.location.LocationGrouping;
import uk.brdr.model.location.LocationType;

public class LocationsDaoImpl implements LocationsDao {

  private final Jdbi jdbi;
  private static final RowMapper<Location> locationRowMapper = new LocationsRowMapper();
  private static final RowMapper<LocationGrouping> locationGroupingRowMapper = new LocationGroupingsRowMapper();

  public LocationsDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public List<Location> getLocations() {
    try {
      return jdbi.withHandle(handle ->
          handle.createQuery("SELECT * FROM locations")
              .map(locationRowMapper)
              .list()
      );
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }

  @Override
  public List<LocationGrouping> getLocationGrouping(LocationType locationType) {
    try {
      var sql = String.format("SELECT * FROM %s", locationType.toString());
      return jdbi.withHandle(handle ->
          handle.createQuery(sql)
              .map(locationGroupingRowMapper)
              .list()
      );
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }
}
