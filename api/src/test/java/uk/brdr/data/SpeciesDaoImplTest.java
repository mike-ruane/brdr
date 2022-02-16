package uk.brdr.data;

import static uk.brdr.utils.DatabaseUtils.getH2DataSource;
import static uk.brdr.utils.DatabaseUtils.loadSpecies;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.brdr.data.daoimpl.SpeciesDaoImpl;

public class SpeciesDaoImplTest {

  static DataSource datasource = getH2DataSource();

  @BeforeAll
  static void setup() throws SQLException, IOException {
    var flyway = Flyway.configure().dataSource(datasource).load();
    flyway.clean();
    flyway.migrate();
    loadSpecies(datasource);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    datasource.getConnection().close();
  }

  @Test
  void getAll() {
    var speciesDaoImpl = new SpeciesDaoImpl(datasource);
    var species = speciesDaoImpl.getAll();
    assert (!species.isEmpty());
  }
}
