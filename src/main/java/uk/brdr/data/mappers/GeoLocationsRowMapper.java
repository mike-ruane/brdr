package uk.brdr.data.mappers;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.location.GeoLocation;

public class GeoLocationsRowMapper implements RowMapper<GeoLocation> {

  @Override
  public GeoLocation map(ResultSet rs, StatementContext ctx) throws SQLException {
    var geo = rs.getArray("geo");
    BigDecimal[][] geoArray = (BigDecimal[][]) geo.getArray();
    return new GeoLocation(rs.getInt("id"), rs.getString("name"), Arrays.asList(geoArray));
  }
}
