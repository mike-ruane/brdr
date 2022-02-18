package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.location.LocationSelect;

public class LocationSelectRowMapper implements RowMapper<LocationSelect> {

  @Override
  public LocationSelect map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new LocationSelect(rs.getInt("id"), rs.getString("name"));
  }
}
