package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import uk.brdr.data.SightingsDao;
import uk.brdr.model.Sighting;

public class SightingController {

  private final SightingsDao sightingsDao;

  public SightingController(SightingsDao sightingsDao) {
    this.sightingsDao = sightingsDao;
  }

  public void addSighting(Context ctx) {
    try {
      var sighting = ctx.bodyAsClass(Sighting.class);
      sightingsDao.addSighting(sighting);
      ctx.status(HttpCode.CREATED);
    } catch (Exception e) {
      throw new InternalServerErrorResponse();
    }
  }

//  public void getSightings(Context ctx) {
//    try {
//      var userId = ctx.pathParam("userId");
//    }
//  }

}
