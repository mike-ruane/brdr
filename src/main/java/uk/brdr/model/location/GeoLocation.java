package uk.brdr.model.location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class GeoLocation {

  int id;
  String name;
  List<BigDecimal[]> geo;

  public GeoLocation(int id, String name, List<BigDecimal[]> geo) {
    this.id = id;
    this.name = name;
    this.geo = geo;
  }

  public GeoLocation() {
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<BigDecimal[]> getGeo() {
    return geo;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGeo(List<BigDecimal[]> geo) {
    this.geo = geo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeoLocation that = (GeoLocation) o;
    return id == that.id && Objects.equals(name, that.name) && Objects.equals(geo,
        that.geo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, geo);
  }

  @Override
  public String toString() {
    return "GeoLocation{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", geo=" + geo +
        '}';
  }
}
