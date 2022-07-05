package uk.brdr.geo;

import static uk.brdr.geo.GeometryTypes.FEATURE_COLLECTION;
import static uk.brdr.geo.GeometryTypes.MULTI_POLYGON;
import static uk.brdr.geo.GeometryTypes.POLYGON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;
import net.postgis.jdbc.geometry.MultiPolygon;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;
import uk.brdr.model.sighting.SightingsByGeometry;

public class SightingsByGeometrySerializer extends JsonSerializer<List<SightingsByGeometry>> {

  @Override
  public void serialize(
      List<SightingsByGeometry> value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    writeTypeField(FEATURE_COLLECTION, gen);
    writeStartFeatures(gen);
    for (SightingsByGeometry sightingsByGeometry : value) {
      gen.writeStartObject();
      writeFeature(sightingsByGeometry, gen);
      gen.writeEndObject();
    }
    gen.writeEndArray();
  }

  protected void writeFeature(SightingsByGeometry sightingsByGeometry, JsonGenerator json)
      throws IOException {
    try {
      writeTypeField("Feature", json);
      json.writeObjectFieldStart("properties");
      json.writeStringField("geometry", sightingsByGeometry.getName());
      json.writeNumberField("speciesCount", sightingsByGeometry.getSpecies().size());
      json.writeNumberField("geometryId", sightingsByGeometry.getGeoId());
      json.writeEndObject();
      json.writeObjectFieldStart("geometry");

      var geom = sightingsByGeometry.getGeometry();
      if (geom instanceof MultiPolygon) {
        serializeMultiPolygon((MultiPolygon) geom, json);
      } else if (geom instanceof Polygon) {
        serializePolygon((Polygon) geom, json);
      }
    } catch (IOException e) {
      throw new RuntimeException("");
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

  protected void writeStartCoordinates(JsonGenerator json) throws IOException {
    json.writeArrayFieldStart("coordinates");
  }

  protected void writeEndCoordinates(JsonGenerator json) throws IOException {
    json.writeEndArray();
  }

  protected void writePoints(JsonGenerator json, Point[] points) throws IOException {
    for (Point point : points) {
      json.writeStartArray();
      writeNumbers(json, point.getX(), point.getY(), point.getZ());
      json.writeEndArray();
    }
  }

  protected void writeNumbers(JsonGenerator json, double... numbers) throws IOException {
    for (double number : numbers) {
      json.writeNumber(number);
    }
  }

  protected void writeTypeField(String type, JsonGenerator json) throws IOException {
    json.writeStringField("type", type);
  }

  protected void writeStartFeatures(JsonGenerator json) throws IOException {
    json.writeArrayFieldStart("features");
  }
}
