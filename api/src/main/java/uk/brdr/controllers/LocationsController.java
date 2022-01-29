package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import uk.brdr.data.LocationsDao;
import uk.brdr.data.SpeciesDao;

public class LocationsController {

  private final LocationsDao locationsDao;

  public LocationsController(LocationsDao locationsDao) {
    this.locationsDao = locationsDao;
  }

  public void getLocations(Context ctx) {
    try {
      var locations = locationsDao.getLocations();
      ctx.json(locations);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
