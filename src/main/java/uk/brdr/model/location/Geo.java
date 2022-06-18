package uk.brdr.model.location;

import java.util.Objects;

public class Geo {

  int id;
  String name;

  public Geo(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Geo() {
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Geo geo = (Geo) o;
    return id == geo.id && Objects.equals(name, geo.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Geo{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
