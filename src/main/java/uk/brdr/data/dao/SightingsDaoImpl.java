package uk.brdr.data.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.mappers.SpeciesRowMapper;
import uk.brdr.data.mappers.UserSightingsRowMapper;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.UserSighting;

public class SightingsDaoImpl implements SightingsDao {

  private final Jdbi jdbi;

  private static final UserSightingsRowMapper USER_SIGHTINGS_ROW_MAPPER =
      new UserSightingsRowMapper();
  private static final SpeciesRowMapper SPECIES_ROW_MAPPER = new SpeciesRowMapper();
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
                        + "(species_id, geo_id, user_id, date) "
                        + "values (:species_id, :geo_id, :user_id, :date)");

            sighting
                .getSpecies()
                .forEach(
                    species ->
                        preparedBatch
                            .bind("species_id", species.intValue())
                            .bind("geo_id", sighting.getGeoId())
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
  public List<UserSighting> getSightings(int userId) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery(
                      "SELECT geo_id AS geo_id, "
                          + "species_id AS species_id "
                          + "FROM sightings "
                          + "WHERE user_id = :user_id")
                  .bind("user_id", userId)
                  .map(USER_SIGHTINGS_ROW_MAPPER)
                  .list());
    } catch (Exception e) {
      throw new RuntimeException("failed to get sighting for user", e);
    }
  }

  @Override
  public List<Species> getSightings(int geoId, int userId) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery(
                      "SELECT sp.id AS id, sp.scientific_name AS scientific_name, "
                          + "sp.preferred_common_name AS preferred_common_name, sp.habitat AS habitat, "
                          + "sp.genus AS genus, sp.family AS family, sp.family_order AS family_order, "
                          + "sp.breeding_population AS breeding_population, "
                          + "sp.winter_visitor_population AS winter_visitor_population "
                          + "FROM sightings si "
                          + "INNER JOIN species sp "
                          + "ON si.species_id = sp.id "
                          + "WHERE si.user_id = :user_id AND si.geo_id = :geo_id")
                  .bindMap(Map.of("user_id", userId, "geo_id", geoId))
                  .map(SPECIES_ROW_MAPPER)
                  .list());
    } catch (Exception e) {
      throw new RuntimeException("failed to get sighting for user", e);
    }
  }

  @Override
  public List<UserSighting> getSightingsByDate(int userId, Date date) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery(
                      "SELECT geo_id AS geo_id, "
                          + "species_id AS species_id "
                          + "FROM sightings "
                          + "WHERE user_id = :user_id AND date = :date")
                  .bindMap(Map.of("user_id", userId, "date", date))
                  .map(USER_SIGHTINGS_ROW_MAPPER)
                  .list());
    } catch (Exception e) {
      throw new RuntimeException("failed to get sighting for user", e);
    }
  }
}
