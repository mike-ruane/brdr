package uk.brdr.model.location;

import java.util.Objects;

public class LocationSelect {

  public int id;
  public String name;

  public LocationSelect(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public LocationSelect() {}

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
    LocationSelect that = (LocationSelect) o;
    return id == that.id && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "LocationSelect{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
