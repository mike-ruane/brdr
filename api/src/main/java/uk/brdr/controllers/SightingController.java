package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import uk.brdr.data.dao.SightingsDao;
import uk.brdr.data.repositories.SightingsOverview;
import uk.brdr.model.Sighting;

public class SightingController {

  private final SightingsDao sightingsDao;
  private final SightingsOverview sightingsOverview;

  public SightingController(SightingsDao sightingsDao, SightingsOverview sightingsOverview) {
    this.sightingsDao = sightingsDao;
    this.sightingsOverview = sightingsOverview;
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

  public void getSightingsOverview(Context ctx) {
    try {
      var userId = Integer.parseInt(ctx.pathParam("userId"));
      var sightings = sightingsOverview.getSightingsForUserByLocation(userId);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }
}