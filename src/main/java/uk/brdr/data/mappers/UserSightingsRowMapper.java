package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.sighting.UserSighting;

public class UserSightingsRowMapper implements RowMapper<UserSighting> {

  @Override
  public UserSighting map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new UserSighting(
        rs.getInt("geo_id"),
        rs.getInt("species_id"));
  }
}
