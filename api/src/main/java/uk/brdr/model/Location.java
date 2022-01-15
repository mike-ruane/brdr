package uk.brdr.model;

import java.util.Objects;

public class Location {

  public int id;
  public String location;
  public String county;

  public Location(int id, String location, String county) {
    this.id = id;
    this.location = location;
    this.county = county;
  }

  public Location() {
  }

  public int getId() {
    return id;
  }

  public String getLocation() {
    return location;
  }

  public String getCounty() {
    return county;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Location location1 = (Location) o;
    return id == location1.id && Objects.equals(location, location1.location)
        && Objects.equals(county, location1.county);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, location, county);
  }

  @Override
  public String toString() {
    return "Location{" +
        "id=" + id +
        ", location='" + location + '\'' +
        ", county='" + county + '\'' +
        '}';
  }
}
