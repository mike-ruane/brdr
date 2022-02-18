package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import uk.brdr.data.dao.LocationsDao;

public class LocationsController {

  private final LocationsDao locationsDao;

  public LocationsController(LocationsDao locationsDao) {
    this.locationsDao = locationsDao;
  }

  public void getLocationsByCounty(Context ctx) {
    try {
      var countyId = Integer.parseInt(ctx.pathParam("countyId"));
      var locations = locationsDao.getLocationByCounty(countyId);
      ctx.json(locations);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }

  public void getCounties(Context ctx) {
    try {
      var counties = locationsDao.getCounties();
      ctx.json(counties);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
