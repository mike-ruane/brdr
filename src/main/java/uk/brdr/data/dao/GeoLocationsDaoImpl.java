package uk.brdr.data.dao;

import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import uk.brdr.data.mappers.GeoLocationsRowMapper;
import uk.brdr.model.location.Geo;
import uk.brdr.model.location.GeoLocation;

public class GeoLocationsDaoImpl implements GeoLocationsDao {

  private final Jdbi jdbi;
  private static final RowMapper<GeoLocation> GEO_LOCATIONS_ROW_MAPPER =
      new GeoLocationsRowMapper();

  public GeoLocationsDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public List<GeoLocation> getGeos(List<Integer> geoIds) {
    try {
      return jdbi.withHandle(
          handle ->
              handle.createQuery("SELECT * FROM geo_locations WHERE id IN (<listOfGeos>)")
                  .bindList("listOfGeos", geoIds)
                  .map(GEO_LOCATIONS_ROW_MAPPER).list());
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }

  @Override
  public List<Geo> getAllGeoNames() {
    try {
      return jdbi.withHandle(
          handle ->
              handle.createQuery("SELECT id, name FROM geo_locations")
                  .map(row ->
                      new Geo(
                          row.getColumn("id", Integer.class),
                          row.getColumn("name", String.class))
                  ).list());
    } catch (Exception e) {
      throw new RuntimeException("error fetching locations from db");
    }
  }
}
