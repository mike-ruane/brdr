package uk.brdr.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.brdr.data.SpeciesDaoImpl;

public class SpeciesDaoImplTest {

  @BeforeAll
  static void setup() throws SQLException, IOException {
    var datasource = getH2DataSource();
    var flyway = Flyway.configure().dataSource(datasource).load();
    flyway.migrate();
    loadSpecies(datasource);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    var datasource = getH2DataSource();
    datasource.getConnection().close();
  }

  @Test
  void getAll() {
    var datasource = getH2DataSource();
    var speciesDaoImpl = new SpeciesDaoImpl(datasource);

    var species = speciesDaoImpl.getAll();
    assert(!species.isEmpty());
  }

  static DataSource getH2DataSource() {
    var jdbcDataSource = new JdbcDataSource();
    jdbcDataSource.setUrl("jdbc:h2:~/brdr");
    jdbcDataSource.setUser("sa");
    jdbcDataSource.setPassword("");

    return jdbcDataSource;
  }

  private static void loadSpecies(DataSource dataSource) throws SQLException, IOException {
    var conn = dataSource.getConnection();
    var sql = "INSERT INTO species "
        + "(scientific_name, preferred_common_name, habitat, genus, family, family_order,"
        + "breeding_population, winter_visitor_population)"
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    var statement = conn.prepareStatement(sql);

    BufferedReader lineReader = new BufferedReader(new FileReader("src/test/resources/species.csv"));
    String lineText;
    lineReader.readLine(); // skip header

    while ((lineText = lineReader.readLine()) != null) {
      var data = lineText.split(",", -1);
      var scientificName = data[0];
      var preferredCommonName = data[1];
      var habitat = data[2];
      var genus = data[3];
      var family = data[4];
      var familyOrder = data[5];
      var breedingPopulation = data[6];
      var winterVisitorPopulation = data[7];

      statement.setString(1, scientificName);
      statement.setString(2, preferredCommonName);
      statement.setString(3, habitat);
      statement.setString(4, genus);
      statement.setString(5, family);
      statement.setString(6, familyOrder);
      statement.setString(7, breedingPopulation);
      statement.setString(8, winterVisitorPopulation);

      statement.execute();
    }
    lineReader.close();
    conn.commit();
    conn.close();
  }
}
