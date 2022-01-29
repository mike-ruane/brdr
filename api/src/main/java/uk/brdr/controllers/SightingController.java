package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.model.Sighting;

public class SightingController {

  private final SightingsDao sightingsDao;

  Logger logger = LoggerFactory.getLogger(SightingController.class);

  public SightingController(SightingsDao sightingsDao) {
    this.sightingsDao = sightingsDao;
  }

  public void addSighting(Context ctx) {
    try {
      logger.info("got request");
      logger.info(ctx.body());
      var sighting = ctx.bodyAsClass(Sighting.class);
      sightingsDao.addSighting(sighting);
      logger.info("successfully added sighting");
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
