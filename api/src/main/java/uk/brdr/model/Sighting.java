package uk.brdr.model;

import java.util.Objects;

public class Sighting {

  public int id;
  public int userId;
  public int speciesId;
  public String city;

  public Sighting(int id, int userId, int speciesId, String city) {
    this.id = id;
    this.userId = userId;
    this.speciesId = speciesId;
    this.city = city;
  }

  public Sighting() {}

  public int getId() {
    return id;
  }

  public int getUserId() {
    return userId;
  }

  public int getSpeciesId() {
    return speciesId;
  }

  public String getCity() {
    return city;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setSpeciesId(int speciesId) {
    this.speciesId = speciesId;
  }

  public void setCity(String city) {
    this.city = city;
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
    return userId == sighting.userId && speciesId == sighting.speciesId && Objects.equals(
        id, sighting.id) && Objects.equals(city, sighting.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, speciesId, city);
  }

  @Override
  public String toString() {
    return "Sighting{" +
        "id='" + id + '\'' +
        ", userId=" + userId +
        ", speciesId=" + speciesId +
        ", city='" + city + '\'' +
        '}';
  }
}