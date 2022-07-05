package uk.brdr.model.sighting;

import java.util.List;
import java.util.Objects;
import net.postgis.jdbc.geometry.Geometry;

public class SightingsByGeometry {

  String name;
  int geoId;
  Geometry geometry;
  List<Integer> species;

  public SightingsByGeometry(String name, int geoId, Geometry geometry, List<Integer> species) {
    this.name = name;
    this.geoId = geoId;
    this.geometry = geometry;
    this.species = species;
  }

  public SightingsByGeometry() {}

  public String getName() {
    return name;
  }

  public int getGeoId() {
    return geoId;
  }

  public Geometry getGeometry() {
    return geometry;
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

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
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
    SightingsByGeometry that = (SightingsByGeometry) o;
    return geoId == that.geoId
        && Objects.equals(name, that.name)
        && Objects.equals(geometry, that.geometry)
        && Objects.equals(species, that.species);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, geoId, geometry, species);
  }

  @Override
  public String toString() {
    return "SightingsByGeometry{"
        + "name='"
        + name
        + '\''
        + ", geoId="
        + geoId
        + ", geometry="
        + geometry
        + ", species="
        + species
        + '}';
  }
}
