package uk.brdr.model.sighting;

import java.math.BigDecimal;
import java.util.List;

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
}
