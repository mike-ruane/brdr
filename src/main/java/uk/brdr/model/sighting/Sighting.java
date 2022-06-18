package uk.brdr.model.sighting;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Sighting {

  public int id;
  public int userId;
  public List<Integer> species;
  public int geoId;
  public Date date;

  public Sighting(int id, int userId, List<Integer> species, int geoId, Date date) {
    this.id = id;
    this.userId = userId;
    this.species = species;
    this.geoId = geoId;
    this.date = date;
  }

  public Sighting() {}

  public int getId() {
    return id;
  }

  public int getUserId() {
    return userId;
  }

  public List<Integer> getSpecies() {
    return species;
  }

  public int getGeoId() {
    return geoId;
  }

  public Date getDate() {
    return date;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setSpecies(List<Integer> species) {
    this.species = species;
  }

  public void setGeoId(int geoId) {
    this.geoId = geoId;
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
    return id == sighting.id
        && userId == sighting.userId
        && geoId == sighting.geoId
        && Objects.equals(species, sighting.species)
        && Objects.equals(date, sighting.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, species, geoId, date);
  }

  @Override
  public String toString() {
    return "Sighting{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", species="
        + species
        + ", geoId="
        + geoId
        + ", date="
        + date
        + '}';
  }
}
