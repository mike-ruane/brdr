package uk.brdr.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.brdr.utils.DatabaseUtils.getH2DataSource;

import java.sql.Date;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.brdr.data.daoimpl.SightingsDaoImpl;
import uk.brdr.model.sighting.Sighting;

public class SightingsDaoImplTest {

  static DataSource datasource = getH2DataSource();

  @BeforeAll
  static void setup() throws SQLException {
    var flyway = Flyway.configure().dataSource(datasource).load();
    flyway.clean();
    flyway.migrate();

    var conn = datasource.getConnection();
    var inertUser =
        conn.prepareStatement(
            "INSERT INTO users (email, password) values ('mike@jruane.com', 'secure-password')");
    inertUser.execute();
    var insertSpecies =
        conn.prepareStatement(
            ""
                + "INSERT INTO species "
                + "(scientific_name, preferred_common_name, habitat, genus, family,"
                + "family_order) "
                + "values "
                + "('Locustella fluviatilis', 'River Warbler', "
                + "'freshwater', 'Locustella', 'Locustellidae', 'Passeriformes')");
    insertSpecies.execute();
    var insertCounty =
        conn.prepareStatement(
            "INSERT INTO counties (name, lat, lon) values ('Lancashire', 23.4, 54.5)");
    insertCounty.execute();
    var insertRegion =
        conn.prepareStatement(
            "INSERT INTO regions (name, lat, lon) values ('North West', 23.4, 54.5)");
    insertRegion.execute();
    var insertCountry =
        conn.prepareStatement(
            "INSERT INTO countries (name, lat, lon) values ('England', 23.4, 54.5)");
    insertCountry.execute();
    var insertLocation =
        conn.prepareStatement(
            "INSERT INTO locations (name, county_id, region_id, country_id, lat, lon) "
                + "values ('Accrington', 1, 1, 1, 45.34, -1.45)");
    insertLocation.execute();
  }

  @AfterAll
  static void tearDown() throws SQLException {
    datasource.getConnection().close();
  }

  @Test
  void insertSighting() throws SQLException {
    var sightingsDao = new SightingsDaoImpl(datasource);
    var sighting = new Sighting(0, 1, 1, 1, Date.valueOf("2022-01-29"));
    sightingsDao.addSighting(sighting);
    var conn = datasource.getConnection();
    var statement = conn.prepareStatement("SELECT * FROM sightings");
    var resultSet = statement.executeQuery();
    while (resultSet.next()) {
      assertEquals(resultSet.getInt("id"), 1);
      assertEquals(resultSet.getInt("user_id"), 1);
      assertEquals(resultSet.getInt("species_id"), 1);
      assertEquals(resultSet.getInt("location_id"), 1);
      assertEquals(resultSet.getDate("date"), Date.valueOf("2022-01-29"));
    }
  }
}
