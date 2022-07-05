package uk.brdr.geo;

import static uk.brdr.geo.GeometryTypes.MULTI_POLYGON;
import static uk.brdr.geo.GeometryTypes.POLYGON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.MultiPolygon;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;

public class GeometrySerializer extends JsonSerializer<Geometry> {

  @Override
  public void serialize(Geometry geom, JsonGenerator json, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    json.writeStartObject();
    if (geom instanceof MultiPolygon) {
      serializeMultiPolygon((MultiPolygon) geom, json);
    } else if (geom instanceof Polygon) {
      serializePolygon((Polygon) geom, json);
    }
    json.writeEndObject();
  }

  protected void serializeMultiPolygon(MultiPolygon mp, JsonGenerator json) throws IOException {
    writeTypeField(MULTI_POLYGON, json);
    writeStartCoordinates(json);

    for (Polygon polygon : mp.getPolygons()) {
      json.writeStartArray();

      for (int i = 0; i < polygon.numRings(); i++) {
        json.writeStartArray();
        writePoints(json, polygon.getRing(i).getPoints());
        json.writeEndArray();
      }

      json.writeEndArray();
    }

    writeEndCoordinates(json);
  }

  protected void serializePolygon(Polygon polygon, JsonGenerator json) throws IOException {
    writeTypeField(POLYGON, json);
    writeStartCoordinates(json);

    for (int i = 0; i < polygon.numRings(); i++) {
      json.writeStartArray();
      writePoints(json, polygon.getRing(i).getPoints());
      json.writeEndArray();
    }

    writeEndCoordinates(json);
  }

  protected void writeTypeField(String type, JsonGenerator json) throws IOException {
    json.writeStringField("type", type);
  }

  protected void writeStartCoordinates(JsonGenerator json) throws IOException {
    json.writeArrayFieldStart("coordinates");
  }

  protected void writeEndCoordinates(JsonGenerator json) throws IOException {
    json.writeEndArray();
  }

  protected void writeNumbers(JsonGenerator json, double... numbers) throws IOException {
    for (double number : numbers) {
      json.writeNumber(number);
    }
  }

  protected void writePoints(JsonGenerator json, Point[] points) throws IOException {
    for (Point point : points) {
      json.writeStartArray();
      writeNumbers(json, point.getX(), point.getY(), point.getZ());
      json.writeEndArray();
    }
  }
}
