package uk.brdr.model.sighting;

import java.sql.Date;
import java.util.Objects;
import uk.brdr.model.Species;

public class SightingDetail {
  
  Date date;
  Species species;

  public SightingDetail(Date date, Species species) {
    this.date = date;
    this.species = species;
  }

  public SightingDetail() {
  }

  public Date getDate() {
    return date;
  }

  public Species getSpecies() {
    return species;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setSpecies(Species species) {
    this.species = species;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SightingDetail that = (SightingDetail) o;
    return Objects.equals(date, that.date) && Objects.equals(species,
        that.species);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, species);
  }

  @Override
  public String toString() {
    return "SightingDetail{" +
        "date=" + date +
        ", species=" + species +
        '}';
  }
}
