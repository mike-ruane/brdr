package uk.brdr.data.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import uk.brdr.data.mappers.SpeciesRowMapper;
import uk.brdr.data.dao.SpeciesDao;
import uk.brdr.model.Species;

public class SpeciesDaoImpl implements SpeciesDao {

  private final Jdbi jdbi;

  private static SpeciesRowMapper speciesRowMapper = new SpeciesRowMapper();

  public SpeciesDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public List<Species> getAll() {
    try {
      return jdbi.withHandle(handle ->
          handle.createQuery("SELECT * FROM species")
              .map(speciesRowMapper)
              .list()
      );
    } catch(Exception e) {
      throw new RuntimeException("error fetching species from db");
    }
  }
}
