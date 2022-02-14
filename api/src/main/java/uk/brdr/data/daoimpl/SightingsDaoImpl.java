package uk.brdr.data.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.data.mappers.SightingsOverviewRowMapper;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingOverview;

public class SightingsDaoImpl implements SightingsDao {

  private final Jdbi jdbi;

  private static SightingsOverviewRowMapper sightingsOverviewRowMapper = new SightingsOverviewRowMapper();
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
  public List<SightingOverview> getSightings(int userId) {
    try {
      return jdbi.withHandle(handle ->
          handle.createQuery(
              "SELECT l.id AS location_id, l.county_id AS county_id, l.region_id AS region_id, l.country_id "
                  + "AS country_id, sp.id AS species_id, sp.preferred_common_name AS species, "
                  + "sp.genus AS genus, si.date AS date "
                  + "FROM sightings si "
                  + "INNER JOIN users u "
                  + "ON si.user_id = u.id "
                  + "INNER JOIN species sp "
                  + "ON si.species_id = sp.id "
                  + "INNER JOIN locations l "
                  + "ON si.location_id = l.id "
                  + "WHERE u.id = :id")
              .bind("id", userId)
              .map(sightingsOverviewRowMapper)
              .list());
    } catch (Exception e) {
      throw new RuntimeException("failed to get sighting overview", e);
    }
  }
}
