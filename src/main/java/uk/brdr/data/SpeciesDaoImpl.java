package uk.brdr.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.model.Species;

public class SpeciesDaoImpl implements SpeciesDao {

  private final DataSource dataSource;

  Logger logger = LoggerFactory.getLogger(SpeciesDaoImpl.class);

  public SpeciesDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<Species> getAll() {
    List<Species> speciesList = new ArrayList<>();
    try(Connection conn = dataSource.getConnection();
        var st = conn.createStatement();
        var rs = st.executeQuery("SELECT * FROM species")) {
      while (rs.next()) {
        Species species = new Species();
        species.setId(rs.getInt("id"));
        species.setScientificName(rs.getString("scientific_name"));
        species.setPreferredCommonName(rs.getString("preferred_common_name"));
        species.setHabitat(rs.getString("habitat"));
        species.setGenus(rs.getString("genus"));
        species.setFamily(rs.getString("family"));
        species.setFamilyOrder(rs.getString("family_order"));
        species.setBreedingPopulation(rs.getString("breeding_population"));
        species.setWinterVisitorPopulation(rs.getString("winter_visitor_population"));
        speciesList.add(species);
      }
    } catch (SQLException throwables) {
      logger.error("failed to fetch species from db: {}", throwables.getMessage());
      throwables.printStackTrace();
      throw new RuntimeException("error fetching species from db");
    }
    return speciesList;
  }
}
