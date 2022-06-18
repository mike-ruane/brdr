package uk.brdr.controllers;

import io.javalin.http.Context;
import uk.brdr.data.dao.GeoLocationsDao;

public class GeosController {

  private final GeoLocationsDao geoLocationsDao;

  public GeosController(GeoLocationsDao geoLocationsDao) {
    this.geoLocationsDao = geoLocationsDao;
  }

  public void getGeoNames(Context ctx) {
    var geos = geoLocationsDao.getAllGeoNames();
    ctx.json(geos);
  }
}
