package uk.brdr.model.location;

import java.math.BigDecimal;
import java.util.Objects;

public class LocationGrouping {

  public int id;
  public String name;
  public BigDecimal lat;
  public BigDecimal lon;

  public LocationGrouping(int id, String name, BigDecimal lat, BigDecimal lon) {
    this.id = id;
    this.name = name;
    this.lat = lat;
    this.lon = lon;
  }

  public LocationGrouping() {}

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getLat() {
    return lat;
  }

  public BigDecimal getLon() {
    return lon;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  public void setLon(BigDecimal lon) {
    this.lon = lon;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationGrouping that = (LocationGrouping) o;
    return id == that.id
        && Objects.equals(name, that.name)
        && Objects.equals(lat, that.lat)
        && Objects.equals(lon, that.lon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lat, lon);
  }

  @Override
  public String toString() {
    return "LocationGrouping{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", lat="
        + lat
        + ", lon="
        + lon
        + '}';
  }
}
