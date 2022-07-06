package uk.brdr.util;

import net.postgis.jdbc.geometry.LinearRing;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;

public class GeometryBuilder {
  public static final int DEFAULT_SRID = 4326;

  public static Polygon createPolygon(Point[] points) {
    Polygon result = new Polygon(new LinearRing[] {new LinearRing(points)});
    result.setSrid(DEFAULT_SRID);
    return result;
  }

  public static Polygon createPolygon(Point[] points, int srid) {
    Polygon result = new Polygon(new LinearRing[] {new LinearRing(points)});
    result.setSrid(srid);
    return result;
  }
}
