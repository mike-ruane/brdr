package uk.brdr.model.sighting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class GeoSighting {

  String name;
  List<BigDecimal[]> geo;
  List<Integer> species;

  public GeoSighting() {
  }

  public GeoSighting(String name, List<BigDecimal[]> geo, List<Integer> species) {
    this.name = name;
    this.geo = geo;
    this.species = species;
  }

  public String getName() {
    return name;
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
    GeoSighting that = (GeoSighting) o;
    return Objects.equals(name, that.name) && Objects.equals(geo, that.geo)
        && Objects.equals(species, that.species);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, geo, species);
  }

  @Override
  public String toString() {
    return "GeoSighting{" +
        "name='" + name + '\'' +
        ", geo=" + geo +
        ", species=" + species +
        '}';
  }
}
