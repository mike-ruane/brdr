package uk.brdr.model.sighting;

import java.util.List;

public class SightingByLocation {

  public String name;
  public double lat;
  public double lon;
  public List<SightingOverview> sightings;

  public SightingByLocation(String name, double lat, double lon,
      List<SightingOverview> sightings) {
    this.name = name;
    this.lat = lat;
    this.lon = lon;
    this.sightings = sightings;
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

  public List<SightingOverview> getSightings() {
    return sightings;
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

  public void setSightings(List<SightingOverview> sightings) {
    this.sightings = sightings;
  }
}
