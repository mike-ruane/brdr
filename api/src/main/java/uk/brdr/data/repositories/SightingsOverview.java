package uk.brdr.data.repositories;

import java.util.List;
import java.util.Map;
import uk.brdr.model.SightingOverview;

public interface SightingsOverview {
  Map<String, List<SightingOverview>> getSightingsForUserByLocation(int userId);
}
