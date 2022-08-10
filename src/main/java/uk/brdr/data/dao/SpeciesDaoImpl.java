package uk.brdr.data.dao;

import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import uk.brdr.data.mappers.SpeciesRowMapper;
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
      var species =
          jdbi.withHandle(
              handle -> handle.createQuery("SELECT * FROM species").map(speciesRowMapper).list());
      Collections.sort(species);
      return species;
    } catch (Exception e) {
      throw new RuntimeException("error fetching species from db");
    }
  }

  @Override
  public List<Species> getSpecies(List<Integer> species) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery("SELECT * FROM species WHERE id IN (<listOfSpecies>)")
                  .bindList("listOfSpecies", species)
                  .map(speciesRowMapper)
                  .list());
    } catch (Exception e) {
      throw new RuntimeException("error fetching species from db");
    }
  }

  @Override
  public Species get(int speciesId) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery("SELECT * FROM species WHERE id = :id")
                  .bind("id", speciesId)
                  .map(speciesRowMapper)
                  .findFirst()
                  .orElseThrow(() -> new Exception("cannot find species")));
    } catch (Exception e) {
      throw new RuntimeException("error fetching species from db");
    }
  }
}
