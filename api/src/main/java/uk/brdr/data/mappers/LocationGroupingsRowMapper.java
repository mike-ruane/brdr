package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.location.Location;
import uk.brdr.model.location.LocationGrouping;

public class LocationGroupingsRowMapper implements RowMapper<LocationGrouping> {

  @Override
  public LocationGrouping map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new LocationGrouping(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getDouble("lat"),
        rs.getDouble("lon")
    );
  }
}
