package uk.brdr.model.sighting;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.List;
import uk.brdr.geo.SightingsByGeometrySerializer;

@JsonSerialize(using = SightingsByGeometrySerializer.class)
public class SightingByGeometryList extends ArrayList<SightingsByGeometry> {
  public SightingByGeometryList(List<SightingsByGeometry> sightingsByGeometries) {
    super(sightingsByGeometries);
  }
}
