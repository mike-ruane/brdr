package uk.brdr.model;

import java.util.Objects;

public class Sighting {

  public int id;
  public int speciesId;
  public int locationId;
  public String date;

  public Sighting(int id, int speciesId, int locationId, String date) {
    this.id = id;
    this.speciesId = speciesId;
    this.locationId = locationId;
    this.date = date;
  }

  public Sighting() {}

  public int getId() {
    return id;
  }

  public int getSpeciesId() {
    return speciesId;
  }

  public int getLocationId() {
    return locationId;
  }

  public String getDate() {
    return date;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setSpeciesId(int speciesId) {
    this.speciesId = speciesId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Sighting sighting = (Sighting) o;
    return speciesId == sighting.speciesId && Objects.equals(
        id, sighting.id) && Objects.equals(locationId, sighting.locationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, speciesId, locationId);
  }

  @Override
  public String toString() {
    return "Sighting{" +
        "id='" + id + '\'' +
        ", speciesId=" + speciesId +
        ", locationId='" + locationId + '\'' +
        '}';
  }
}