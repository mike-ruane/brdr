package uk.brdr.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static uk.brdr.utils.DatabaseUtils.getH2DataSource;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.brdr.data.dao.SightingsDaoImpl;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.utils.DatabaseUtils;

public class SightingsDaoImplTest {

  static DataSource datasource = getH2DataSource();

  @BeforeAll
  static void setup() throws SQLException, IOException {
    var flyway = Flyway.configure().dataSource(datasource).load();
    flyway.clean();
    flyway.migrate();

    var conn = datasource.getConnection();
    var inertUser =
        conn.prepareStatement(
            "INSERT INTO users (email, password) values ('mike@jruane.com', 'secure-password')");
    inertUser.execute();
    DatabaseUtils.loadSpecies(datasource);
    DatabaseUtils.loadLocationGrouping(datasource, "counties");
    DatabaseUtils.loadLocationGrouping(datasource, "regions");
    DatabaseUtils.loadLocationGrouping(datasource, "countries");
    DatabaseUtils.loadLocations(datasource);
  }

  @AfterEach
  void afterEach() throws SQLException {
    var conn = datasource.getConnection();
    conn.prepareStatement("DELETE FROM SIGHTINGS").execute();
  }

  @AfterAll
  static void tearDown() throws SQLException {
    datasource.getConnection().close();
  }

  @Test
  void insertSighting() throws SQLException {
    var sightingsDao = new SightingsDaoImpl(datasource);
    var sighting = new Sighting(0, 1, List.of(1), 1, Date.valueOf("2022-01-29"));
    sightingsDao.addSighting(sighting);
    var conn = datasource.getConnection();
    var statement = conn.prepareStatement("SELECT * FROM sightings");
    var resultSet = statement.executeQuery();
    while (resultSet.next()) {
      assertEquals(resultSet.getInt("user_id"), 1);
      assertEquals(resultSet.getInt("species_id"), 1);
      assertEquals(resultSet.getInt("location_id"), 1);
      assertEquals(resultSet.getDate("date"), Date.valueOf("2022-01-29"));
    }
  }

  @Test
  void failInsertSightingOnDuplicate() {
    var sightingsDao = new SightingsDaoImpl(datasource);
    var sighting = new Sighting(0, 1, List.of(1), 1, Date.valueOf("2022-01-29"));
    sightingsDao.addSighting(sighting);
    assertThrows(IllegalStateException.class, () -> sightingsDao.addSighting(sighting));
  }
}
