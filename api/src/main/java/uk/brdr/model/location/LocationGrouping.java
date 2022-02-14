package uk.brdr.model.location;

import java.util.Objects;

public class LocationGrouping {

  public int id;
  public String name;
  public double lat;
  public double lon;

  public LocationGrouping(int id, String name, double lat, double lon) {
    this.id = id;
    this.name = name;
    this.lat = lat;
    this.lon = lon;
  }

  public LocationGrouping() {
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getLat() {
    return lat;
  }

  public double getLon() {
    return lon;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public void setLon(double lon) {
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
    return id == that.id && Double.compare(that.lat, lat) == 0
        && Double.compare(that.lon, lon) == 0 && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lat, lon);
  }

  @Override
  public String toString() {
    return "LocationGrouping{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", lat=" + lat +
        ", lon=" + lon +
        '}';
  }
}
