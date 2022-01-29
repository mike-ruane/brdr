package uk.brdr.model;

import java.sql.Date;
import java.util.Objects;

public class Sighting {

  public int id;
  public int userId;
  public int speciesId;
  public int locationId;
  public Date date;

  public Sighting(int id, int userId, int speciesId, int locationId, Date date) {
    this.id = id;
    this.userId = userId;
    this.speciesId = speciesId;
    this.locationId = locationId;
    this.date = date;
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

  public int getLocationId() {
    return locationId;
  }

  public Date getDate() {
    return date;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUser_id(int userId) {
    this.userId = userId;
  }

  public void setSpeciesId(int speciesId) {
    this.speciesId = speciesId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public void setDate(Date date) {
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
    return id == sighting.id && userId == sighting.userId && speciesId == sighting.speciesId
        && locationId == sighting.locationId && Objects.equals(date, sighting.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, speciesId, locationId, date);
  }

  @Override
  public String toString() {
    return "Sighting{" +
        "id=" + id +
        ", userId=" + userId +
        ", speciesId=" + speciesId +
        ", locationId=" + locationId +
        ", date='" + date + '\'' +
        '}';
  }
}