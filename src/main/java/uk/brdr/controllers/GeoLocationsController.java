package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import uk.brdr.data.dao.GeoLocationsDao;

public class GeoLocationsController {

  private final GeoLocationsDao geoLocationsDao;

  public GeoLocationsController(GeoLocationsDao geoLocationsDao) {
    this.geoLocationsDao = geoLocationsDao;
  }

  public void getAll(Context ctx) {
    try {
      var geoLocations = geoLocationsDao.getAll();
      ctx.json(geoLocations);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
