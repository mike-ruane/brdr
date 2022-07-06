package uk.brdr.model.location;

import java.util.Objects;
import net.postgis.jdbc.PGgeometry;

public class GeometryLocation extends Geo {

  PGgeometry pGgeometry;

  public GeometryLocation(int id, String name, PGgeometry pGgeometry) {
    super(id, name);
    this.pGgeometry = pGgeometry;
  }

  public GeometryLocation() {}

  public PGgeometry getpGgeometry() {
    return pGgeometry;
  }

  public void setpGgeometry(PGgeometry pGgeometry) {
    this.pGgeometry = pGgeometry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    GeometryLocation that = (GeometryLocation) o;
    return Objects.equals(pGgeometry, that.pGgeometry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), pGgeometry);
  }

  @Override
  public String toString() {
    return "GeometryLocation{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", pGgeometry="
        + pGgeometry
        + '}';
  }
}
