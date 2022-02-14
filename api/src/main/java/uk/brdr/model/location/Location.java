package uk.brdr.model.location;

import java.util.Objects;

public class Location {

  public int id;
  public String name;
  public int countyId;
  public int regionId;
  public int countryId;
  public double lat;
  public double lon;

  public Location(int id, String name, int countyId, int regionId, int countryId, double lat,
      double lon) {
    this.id = id;
    this.name = name;
    this.countyId = countyId;
    this.regionId = regionId;
    this.countryId = countryId;
    this.lat = lat;
    this.lon = lon;
  }

  public Location() {
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCountyId() {
    return countyId;
  }

  public int getRegionId() {
    return regionId;
  }

  public int getCountryId() {
    return countryId;
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

  public void setCountyId(int countyId) {
    this.countyId = countyId;
  }

  public void setRegionId(int regionId) {
    this.regionId = regionId;
  }

  public void setCountryId(int countryId) {
    this.countryId = countryId;
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
    Location location = (Location) o;
    return id == location.id && countyId == location.countyId && regionId == location.regionId
        && countryId == location.countryId && Double.compare(location.lat, lat) == 0
        && Double.compare(location.lon, lon) == 0 && Objects.equals(name,
        location.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, countyId, regionId, countryId, lat, lon);
  }

  @Override
  public String toString() {
    return "Location{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", countyId=" + countyId +
        ", regionId=" + regionId +
        ", countryId=" + countryId +
        ", lat=" + lat +
        ", lon=" + lon +
        '}';
  }
}
