package uk.brdr.data.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.model.Sighting;

public class SightingsDaoImpl implements SightingsDao {

  private final Jdbi jdbi;

  Logger logger = LoggerFactory.getLogger(SightingsDaoImpl.class);

  public SightingsDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public void addSighting(Sighting sighting) {
    try {
      jdbi.useHandle(handle ->
          handle.createUpdate(
                  "INSERT INTO sightings (species_id, location_id, date, user_id) "
                      + "values (:species_id, :location_id, :date, :user_id)")
              .bind("species_id", sighting.getSpeciesId())
              .bind("location_id", sighting.getLocationId())
              .bind("date", sighting.getDate())
              .bind("user_id", sighting.getUserId())
              .execute());
    } catch(Exception e) {
      logger.error("failed to add sighting {}, error: {}", sighting.toString(), e.getMessage());
      throw new RuntimeException("failed to add sighting: {}", e.getCause());
    }
  }

  @Override
  public List<Sighting> getSightings() {
    return null;
  }
}
