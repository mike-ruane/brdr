package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.postgis.jdbc.PGgeometry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.location.GeometryLocation;

public class GeometryLocationsRowMapper implements RowMapper<GeometryLocation> {

  @Override
  public GeometryLocation map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new GeometryLocation(
        rs.getInt("id"), rs.getString("name"), (PGgeometry) rs.getObject(3));
  }
}
