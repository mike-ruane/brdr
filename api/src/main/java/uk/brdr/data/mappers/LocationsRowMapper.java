package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.Location;
import uk.brdr.model.Species;

public class LocationsRowMapper implements RowMapper<Location> {

  @Override
  public Location map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new Location(
        rs.getInt("id"),
        rs.getString("location"),
        rs.getString("county")
    );
  }
}
