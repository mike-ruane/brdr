package uk.brdr.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.brdr.utils.DatabaseUtils.getH2DataSource;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.brdr.model.Sighting;

public class SightingsDaoImplTest {

  static DataSource datasource = getH2DataSource();

  @BeforeAll
  static void setup() {
    var flyway = Flyway.configure().dataSource(datasource).load();
    flyway.clean();
    flyway.migrate();
  }

  @AfterAll
  static void tearDown() throws SQLException {
    datasource.getConnection().close();
  }

  @Test
  void insertSighting() throws SQLException {
    var sightingsDao = new SightingsDaoImpl(datasource);
    var sighting = new Sighting(0, 456, 1, "2022-01-29");
    sightingsDao.addSighting(sighting);
    var conn = datasource.getConnection();
    var statement = conn.prepareStatement("SELECT * FROM sightings");
    var resultSet = statement.executeQuery();
    while (resultSet.next()) {
      assertEquals(resultSet.getInt("id"), 1);
      assertEquals(resultSet.getInt("user_id"), 123);
      assertEquals(resultSet.getInt("species_id"), 456);
      assertEquals(resultSet.getInt("location"), 1);
    }
  }
}
