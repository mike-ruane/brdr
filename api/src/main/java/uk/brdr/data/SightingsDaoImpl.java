package uk.brdr.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.model.Sighting;

public class SightingsDaoImpl implements SightingsDao {

  private final DataSource dataSource;

  Logger logger = LoggerFactory.getLogger(SightingsDaoImpl.class);

  public SightingsDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void addSighting(Sighting sighting) {
    try {
      Connection conn = dataSource.getConnection();
      var query = "INSERT INTO sightings (species_id, location_id, date) values (?, ?, ?)";
      var preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, sighting.getSpeciesId());
      preparedStmt.setInt(2, sighting.getLocationId());
      preparedStmt.setDate(3, Date.valueOf(sighting.getDate()));

      preparedStmt.execute();
      conn.close();
    } catch (SQLException e) {
      logger.error("failed to add sighting {}, error: {}", sighting.toString(), e.getMessage());
      throw new RuntimeException("failed to add sighting: {}", e.getCause());
    }
  }

  @Override
  public List<Sighting> getSightings() {
    return null;
  }
}
