package uk.brdr.data.repositories;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.model.SightingOverview;

public class SightingsOverviewImpl implements SightingsOverview {

  private final SightingsDao sightingsDao;

  public SightingsOverviewImpl(SightingsDao sightingsDao) {
    this.sightingsDao = sightingsDao;
  }

  @Override
  public Map<String, List<SightingOverview>> getSightingsForUserByLocation(int userId) {
    var sightings = sightingsDao.getSightings(userId);
    return sightings.stream().collect(groupingBy(SightingOverview::getCounty));
  }
}
