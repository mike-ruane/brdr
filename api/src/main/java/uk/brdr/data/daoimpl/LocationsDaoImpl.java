package uk.brdr.data.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import uk.brdr.data.dao.LocationsDao;
import uk.brdr.data.mappers.LocationsRowMapper;
import uk.brdr.model.Location;

public class LocationsDaoImpl implements LocationsDao {

  private final Jdbi jdbi;
  private static final RowMapper<Location> locationRowMapper = new LocationsRowMapper();

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
}
