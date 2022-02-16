package uk.brdr.data.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.brdr.utils.DatabaseUtils.getH2DataSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.brdr.data.dao.LocationsDao;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.data.daoimpl.LocationsDaoImpl;
import uk.brdr.data.daoimpl.SightingsDaoImpl;
import uk.brdr.model.location.LocationType;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingByLocation;
import uk.brdr.model.sighting.SightingOverview;
import uk.brdr.utils.DatabaseUtils;

public class SightingsOverviewImplTest {

  static DataSource datasource = getH2DataSource();
  SightingsDao sightingsDao;
  SightingsOverview sightingsOverview;
  LocationsDao locationsDao;

  @BeforeAll
  static void setup() throws SQLException, IOException {
    var flyway = Flyway.configure().dataSource(datasource).load();
    flyway.clean();
    flyway.migrate();

    var conn = datasource.getConnection();
    var insertUser = conn.prepareStatement(
        "INSERT INTO users (email, password) values ('mike@jruane.com', 'secure-password')");
    insertUser.execute();
    DatabaseUtils.loadSpecies(datasource);
    DatabaseUtils.loadLocationGrouping(datasource, "counties");
    DatabaseUtils.loadLocationGrouping(datasource, "regions");
    DatabaseUtils.loadLocationGrouping(datasource, "countries");
    DatabaseUtils.loadLocations(datasource);
  }

  @Test
  void getSightingsByLocation() {
    var sightings = List.of(
        new Sighting(0, 1, 23, 8, Date.valueOf("2022-02-15")),
        new Sighting(0, 1, 64, 56, Date.valueOf("2022-02-15")),
        new Sighting(0, 1, 13, 56, Date.valueOf("2022-02-15")),
        new Sighting(0, 1, 78, 56, Date.valueOf("2022-02-15")),
        new Sighting(0, 1, 12, 8, Date.valueOf("2022-02-15")),
        new Sighting(0, 1, 5, 8, Date.valueOf("2022-02-15")),
        new Sighting(0, 1, 45, 8, Date.valueOf("2022-02-15"))
    );

    sightingsDao = new SightingsDaoImpl(datasource);
    locationsDao = new LocationsDaoImpl(datasource);
    sightingsOverview = new SightingsOverviewImpl(sightingsDao, locationsDao);

    sightings.forEach(sightingsDao::addSighting);
    var sightingsByLocation = sightingsOverview.getSightingsForUserByLocation(1, LocationType.LOCATIONS);
    var expected = List.of(
        new SightingByLocation(
            "Abbots Worthy",
            BigDecimal.valueOf(5109132, 5),
            BigDecimal.valueOf(-129097, 5),
            List.of(
                new SightingOverview(56, 29, 1, 1, 64, "Greenish Warbler", "Phylloscopus", Date.valueOf("2022-02-15")),
                new SightingOverview(56, 29, 1, 1, 13, "Pine Grosbeak", "Pinicola", Date.valueOf("2022-02-15")),
                new SightingOverview(56, 29, 1, 1, 78, "Philadelphia Vireo", "Vireo", Date.valueOf("2022-02-15"))
            )
        ),
        new SightingByLocation(
            "Abbess Roding",
            BigDecimal.valueOf(5177815, 5),
            BigDecimal.valueOf(27685, 5),
            List.of(
                new SightingOverview(8, 4, 4, 1, 23, "Greenfinch", "Chloris", Date.valueOf("2022-02-15")),
                new SightingOverview(8, 4, 4, 1, 12, "Chaffinch", "Fringilla", Date.valueOf("2022-02-15")),
                new SightingOverview(8, 4, 4, 1, 5, "Pallas's Grasshopper Warbler", "Helopsaltes", Date.valueOf("2022-02-15")),
                new SightingOverview(8, 4, 4, 1, 45, "Rose-breasted Grosbeak", "Pheucticus", Date.valueOf("2022-02-15"))
            ))
    );

    assertEquals(sightingsByLocation.size(), expected.size());
    assertEquals(expected, sightingsByLocation);
  }

}
