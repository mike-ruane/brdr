package uk.brdr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.sql.SQLException;
import javax.sql.DataSource;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.Polygon;
import org.junit.jupiter.api.Test;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.geo.GeometrySerializer;
import uk.brdr.properties.DatabaseProperties;

public class PolygonTest {

  DatabaseConfiguration dbConfig =
      new DatabaseConfiguration(
          DatabaseProperties.forTesting("jdbc:postgresql://localhost:5432/brdr", "mruane", ""));
  DataSource dataSource = dbConfig.getDatasource();

  @Test
  void parseGeometry() throws SQLException, JsonProcessingException {
    var conn = dataSource.getConnection();
    ((org.postgresql.PGConnection) conn).addDataType("geometry", PGgeometry.class);

    var statement = conn.createStatement();
    var resultSet = statement.executeQuery("select name, geo from polygon_test LIMIT 30");

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("MyModule");
    module.addSerializer(Geometry.class, new GeometrySerializer());
    mapper.registerModule(module);

    while (resultSet.next()) {
      String name = resultSet.getString(1);
      PGgeometry geo = (PGgeometry) resultSet.getObject(2);
      System.out.println(name + ":");
      System.out.println(geo.toString());
      System.out.println(mapper.writeValueAsString(geo.getGeometry()));
      System.out.println(geo.getGeometry() instanceof Polygon);
    }
  }
}
