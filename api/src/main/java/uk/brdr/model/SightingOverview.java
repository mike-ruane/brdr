package uk.brdr.model;

import java.sql.Date;
import java.util.Objects;

public class SightingOverview {

  public String location;
  public String county;
  public int speciesId;
  public String species;
  public String genus;
  public Date date;

  public SightingOverview(String location, String county, int speciesId, String species, String genus, Date date) {
    this.location = location;
    this.county = county;
    this.speciesId = speciesId;
    this.species = species;
    this.genus = genus;
    this.date = date;
  }

  public String getLocation() {
    return location;
  }

  public String getCounty() {
    return county;
  }

  public int getSpeciesId() {
    return speciesId;
  }

  public String getSpecies() {
    return species;
  }

  public String getGenus() {
    return genus;
  }

  public Date getDate() {
    return date;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public void setSpeciesId(int speciesId) {
    this.speciesId = speciesId;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public void setGenus(String genus) {
    this.genus = genus;
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
    SightingOverview that = (SightingOverview) o;
    return speciesId == that.speciesId && Objects.equals(location, that.location)
        && Objects.equals(county, that.county) && Objects.equals(species,
        that.species) && Objects.equals(genus, that.genus) && Objects.equals(date,
        that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, county, speciesId, species, genus, date);
  }

  @Override
  public String toString() {
    return "SightingOverview{" +
        "location='" + location + '\'' +
        ", county='" + county + '\'' +
        ", speciesId=" + speciesId +
        ", species='" + species + '\'' +
        ", genus='" + genus + '\'' +
        ", date=" + date +
        '}';
  }
}
