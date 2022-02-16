package uk.brdr.model.sighting;

import java.sql.Date;
import java.util.Objects;

public class SightingOverview {

  public int locationId;
  public int countyId;
  public int regionId;
  public int countryId;
  public int speciesId;
  public String species;
  public String genus;
  public Date date;

  public SightingOverview(
      int locationId,
      int countyId,
      int regionId,
      int countryId,
      int speciesId,
      String species,
      String genus,
      Date date) {
    this.locationId = locationId;
    this.countyId = countyId;
    this.regionId = regionId;
    this.countryId = countryId;
    this.speciesId = speciesId;
    this.species = species;
    this.genus = genus;
    this.date = date;
  }

  public int getLocationId() {
    return locationId;
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

  public void setLocationId(int locationId) {
    this.locationId = locationId;
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
    return locationId == that.locationId
        && countyId == that.countyId
        && regionId == that.regionId
        && countryId == that.countryId
        && speciesId == that.speciesId
        && Objects.equals(species, that.species)
        && Objects.equals(genus, that.genus)
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(locationId, countyId, regionId, countryId, speciesId, species, genus, date);
  }

  @Override
  public String toString() {
    return "SightingOverview{"
        + "locationId="
        + locationId
        + ", countyId="
        + countyId
        + ", regionId="
        + regionId
        + ", countryId="
        + countryId
        + ", speciesId="
        + speciesId
        + ", species='"
        + species
        + '\''
        + ", genus='"
        + genus
        + '\''
        + ", date="
        + date
        + '}';
  }
}
