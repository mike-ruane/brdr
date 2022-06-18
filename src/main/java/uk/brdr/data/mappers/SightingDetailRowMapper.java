package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.SightingDetail;

public class SightingDetailRowMapper implements RowMapper<SightingDetail> {

  @Override
  public SightingDetail map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new SightingDetail(
        rs.getDate("date"),
        new Species(
        rs.getInt("id"),
        rs.getString("scientific_name"),
        rs.getString("common_name"),
        rs.getString("habitat"),
        rs.getString("genus"),
        rs.getString("family"),
        rs.getString("family_order"),
        rs.getString("breeding_population"),
        rs.getString("winter_visitor_population")));
  }
}