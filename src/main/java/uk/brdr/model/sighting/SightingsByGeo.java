package uk.brdr.model.sighting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SightingsByGeo {

  String name;
  int geoId;
  List<BigDecimal[]> geo;
  List<Integer> species;

  public SightingsByGeo() {}

  public SightingsByGeo(String name, int geoId, List<BigDecimal[]> geo, List<Integer> species) {
    this.name = name;
    this.geoId = geoId;
    this.geo = geo;
    this.species = species;
  }

  public String getName() {
    return name;
  }

  public int getGeoId() {
    return geoId;
  }

  public List<BigDecimal[]> getGeo() {
    return geo;
  }

  public List<Integer> getSpecies() {
    return species;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGeoId(int geoId) {
    this.geoId = geoId;
  }

  public void setGeo(List<BigDecimal[]> geo) {
    this.geo = geo;
  }

  public void setSpecies(List<Integer> species) {
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
    SightingsByGeo that = (SightingsByGeo) o;
    return geoId == that.geoId
        && Objects.equals(name, that.name)
        && Objects.equals(geo, that.geo)
        && Objects.equals(species, that.species);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, geoId, geo, species);
  }

  @Override
  public String toString() {
    return "SightingsByGeo{"
        + "name='"
        + name
        + '\''
        + ", geoId="
        + geoId
        + ", geo="
        + geo
        + ", species="
        + species
        + '}';
  }
}
