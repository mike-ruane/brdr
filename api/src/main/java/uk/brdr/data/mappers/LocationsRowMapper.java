package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.location.Location;

public class LocationsRowMapper implements RowMapper<Location> {

  @Override
  public Location map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new Location(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getInt("county_id"),
        rs.getInt("region_id"),
        rs.getInt("country_id"),
        rs.getDouble("lat"),
        rs.getDouble("lon")
    );
  }
}
