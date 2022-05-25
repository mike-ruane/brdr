package uk.brdr.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import uk.brdr.model.Species;

public class SpeciesRowMapper implements RowMapper<Species> {

  @Override
  public Species map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new Species(
        rs.getInt("id"),
        rs.getString("scientific_name"),
        rs.getString("preferred_common_name"),
        rs.getString("habitat"),
        rs.getString("genus"),
        rs.getString("family"),
        rs.getString("family_order"),
        rs.getString("breeding_population"),
        rs.getString("winter_visitor_population"));
  }
}