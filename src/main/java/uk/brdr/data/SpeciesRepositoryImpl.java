package uk.brdr.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.model.Bird;

public class SpeciesRepositoryImpl implements SpeciesRepository {

  private final List<Bird> birds;
  private final Connection connection;

  Logger logger = LoggerFactory.getLogger(SpeciesRepositoryImpl.class);
  public SpeciesRepositoryImpl(Connection connection) {
    this.birds = new ArrayList<Bird>();
    this.connection = connection;
  }

  @Override
  public List<Bird> getSpecies() {
    if (birds.isEmpty()) {
      logger.info("birds is empty, reading from database");
      try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM species");
        while (rs.next()) {
          Bird bird = new Bird();
          bird.setId(rs.getInt("id"));
          bird.setScientificName(rs.getString("scientific_name"));
          bird.setPreferredCommonName(rs.getString("preferred_common_name"));
          bird.setHabitat(rs.getString("habitat"));
          bird.setGenus(rs.getString("genus"));
          bird.setFamily(rs.getString("family"));
          bird.setFamilyOrder(rs.getString("family_order"));
          bird.setBreedingPopulation(rs.getString("breeding_population"));
          bird.setWinterVisitorPopulation(rs.getString("winter_visitor_population"));
          birds.add(bird);
        }
        rs.close();
        st.close();
        return birds;
      } catch (SQLException throwables) {
        logger.error("failed to fetch species from db: {}", throwables.getMessage());
        throwables.printStackTrace();
      }
    }
    return birds;
  }
}
