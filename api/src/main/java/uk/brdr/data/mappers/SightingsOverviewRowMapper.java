package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.sighting.SightingOverview;

public class SightingsOverviewRowMapper implements RowMapper<SightingOverview> {

  @Override
  public SightingOverview map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new SightingOverview(
        rs.getInt("location_id"),
        rs.getInt("county_id"),
        rs.getInt("region_id"),
        rs.getInt("country_id"),
        rs.getInt("species_id"),
        rs.getString("species"),
        rs.getString("genus"),
        rs.getDate("date"));
  }
}
