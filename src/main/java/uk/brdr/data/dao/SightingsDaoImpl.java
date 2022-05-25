package uk.brdr.data.dao;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.mappers.SightingForUserRowMapper;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingForUser;

public class SightingsDaoImpl implements SightingsDao {

  private final Jdbi jdbi;

  private static SightingForUserRowMapper sightingForUserRowMapper = new SightingForUserRowMapper();
  Logger logger = LoggerFactory.getLogger(SightingsDaoImpl.class);

  public SightingsDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public void addSighting(Sighting sighting) {
    try {
      jdbi.useHandle(
          handle -> {
            var preparedBatch =
                handle.prepareBatch(
                    "INSERT INTO sightings "
                        + "(species_id, location_id, user_id, date) "
                        + "values (:species_id, :location_id, :user_id, :date)");

            sighting
                .getSpecies()
                .forEach(
                    species ->
                        preparedBatch
                            .bind("species_id", species.intValue())
                            .bind("location_id", sighting.getLocationId())
                            .bind("user_id", sighting.getUserId())
                            .bind("date", sighting.getDate())
                            .add());
            preparedBatch.execute();
          });
    } catch (Exception e) {
      logger.error("failed to add sighting {}, error: {}", sighting, e.getMessage());
      throw e;
    }
  }

  @Override
  public List<SightingForUser> getGeoSightings(int userId) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery(
                      "SELECT g.id AS geo_id, "
                          + "sp.id AS species_id "
                          + "FROM sightings si "
                          + "INNER JOIN users u "
                          + "ON si.user_id = u.id "
                          + "INNER JOIN species sp "
                          + "ON si.species_id = sp.id "
                          + "INNER JOIN geo_locations g "
                          + "ON si.location_id = g.id "
                          + "WHERE u.id = :id")
                  .bind("id", userId)
                  .map(sightingForUserRowMapper)
                  .list());
    } catch (Exception e) {
      throw new RuntimeException("failed to get sighting for user", e);
    }
  }
}
