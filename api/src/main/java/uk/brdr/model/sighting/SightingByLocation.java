package uk.brdr.model.sighting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SightingByLocation {

  public String name;
  public BigDecimal lat;
  public BigDecimal lon;
  public List<SightingOverview> sightings;

  public SightingByLocation(
      String name, BigDecimal lat, BigDecimal lon, List<SightingOverview> sightings) {
    this.name = name;
    this.lat = lat;
    this.lon = lon;
    this.sightings = sightings;
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

  public List<SightingOverview> getSightings() {
    return sightings;
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

  public void setSightings(List<SightingOverview> sightings) {
    this.sightings = sightings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SightingByLocation that = (SightingByLocation) o;
    return Objects.equals(name, that.name)
        && Objects.equals(lat, that.lat)
        && Objects.equals(lon, that.lon)
        && Objects.equals(sightings, that.sightings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, lat, lon, sightings);
  }

  @Override
  public String toString() {
    return "SightingByLocation{"
        + "name='"
        + name
        + '\''
        + ", lat="
        + lat
        + ", lon="
        + lon
        + ", sightings="
        + sightings
        + '}';
  }
}
