package uk.brdr.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.model.Location;

public class LocationsDaoImpl implements LocationsDao {

  private final DataSource dataSource;

  Logger logger = LoggerFactory.getLogger(LocationsDaoImpl.class);

  public LocationsDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<Location> getLocations() {
    List<Location> locations = new ArrayList<>();
    try(Connection conn = dataSource.getConnection();
        var st = conn.createStatement();
        var rs = st.executeQuery("SELECT * FROM locations")) {
      while (rs.next()) {
        Location location = new Location();
        location.setId(rs.getInt("id"));
        location.setLocation(rs.getString("location"));
        location.setCounty(rs.getString("county"));
        locations.add(location);
      }
    } catch (SQLException throwables) {
      logger.error("failed to fetch locations from db: {}", throwables.getMessage());
      throwables.printStackTrace();
      throw new RuntimeException("error fetching locations from db");
    }
    return locations;
  }
}
