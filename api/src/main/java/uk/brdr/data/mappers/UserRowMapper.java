package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.User;

public class UserRowMapper implements RowMapper<User> {

  @Override
  public User map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new User(
        rs.getInt("id"),
        rs.getString("email"),
        rs.getString("password")
    );
  }
}
