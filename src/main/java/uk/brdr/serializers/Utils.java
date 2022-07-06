package uk.brdr.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.InternalServerErrorResponse;
import java.util.List;
import uk.brdr.model.sighting.SightingByGeometryList;
import uk.brdr.model.sighting.SightingsByGeometry;

public class Utils {

  private static ObjectMapper objectMapper = new ObjectMapper();

  public static String serializeSightings(List<SightingsByGeometry> sightings) {
    var serializable = new SightingByGeometryList(sightings);
    try {
      return objectMapper.writeValueAsString(serializable);
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorResponse("unable to serialize to geojson");
    }
  }
}
