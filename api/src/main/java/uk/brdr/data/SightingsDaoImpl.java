package uk.brdr.data;

import java.sql.Connection;
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
      var query = "INSERT INTO sightings (user_id, species_id, city) values (?, ? ,?)";
      var preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, sighting.getUserId());
      preparedStmt.setInt(2, sighting.getSpeciesId());
      preparedStmt.setString(3, sighting.getCity());

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
