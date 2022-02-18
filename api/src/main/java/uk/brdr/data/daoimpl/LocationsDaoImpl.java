package uk.brdr.data.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import uk.brdr.data.dao.LocationsDao;
import uk.brdr.data.mappers.LocationGroupingsRowMapper;
import uk.brdr.data.mappers.LocationRowMapper;
import uk.brdr.data.mappers.LocationSelectRowMapper;
import uk.brdr.model.location.Location;
import uk.brdr.model.location.LocationGrouping;
import uk.brdr.model.location.LocationSelect;
import uk.brdr.model.location.LocationType;

public class LocationsDaoImpl implements LocationsDao {

  private final Jdbi jdbi;
  private static final RowMapper<LocationSelect> locationSelectRowMapper =
      new LocationSelectRowMapper();
  private static final RowMapper<Location> locationRowMapper = new LocationRowMapper();
  private static final RowMapper<LocationGrouping> locationGroupingRowMapper =
      new LocationGroupingsRowMapper();

  public LocationsDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public List<LocationSelect> getLocationByCounty(int countyId) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery(
                      "SELECT * FROM locations l "
                          + "INNER JOIN counties c "
                          + "ON l.county_id  = c.id "
                          + "WHERE c.id = :id")
                  .bind("id", countyId)
                  .map(locationSelectRowMapper)
                  .list());
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }

  @Override
  public List<LocationSelect> getCounties() {
    try {
      return jdbi.withHandle(
          handle ->
              handle.createQuery("SELECT * FROM counties").map(locationSelectRowMapper).list());
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }

  @Override
  public List<LocationGrouping> getLocationGrouping(LocationType locationType) {
    try {
      var sql = String.format("SELECT * FROM %s", locationType.toString());
      return jdbi.withHandle(
          handle -> handle.createQuery(sql).map(locationGroupingRowMapper).list());
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }
}
