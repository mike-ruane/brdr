package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.data.repositories.SightingsOverview;
import uk.brdr.model.Location;
import uk.brdr.model.SightingOverview;

public class SightingsOverviewRowMapper implements RowMapper<SightingOverview> {

  @Override
  public SightingOverview map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new SightingOverview(
        rs.getString("location"),
        rs.getString("county"),
        rs.getInt("species_id"),
        rs.getString("species"),
        rs.getString("genus"),
        rs.getDate("date")
    );
  }
}
