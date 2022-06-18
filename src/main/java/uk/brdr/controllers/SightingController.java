package uk.brdr.controllers;

import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import uk.brdr.handlers.JwtCookieHandler;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.services.SightingsService;

public class SightingController {

  private final SightingsService sightingsService;

  public SightingController(SightingsService sightingsService) {
    this.sightingsService = sightingsService;
  }

  public void addSighting(Context ctx) {
    try {
      var sighting = ctx.bodyAsClass(Sighting.class);
      var userId = Integer.parseInt(JwtCookieHandler.getDecodedFromContext(ctx).getIssuer());
      sighting.setUserId(userId);
      sightingsService.addSighting(sighting);
      ctx.status(HttpCode.CREATED);
    } catch (IllegalStateException e) {
      throw new ConflictResponse();
    } catch (Exception e) {
      throw new InternalServerErrorResponse();
    }
  }

  public void getSightingsByGeo(Context ctx) {
    try {
      var userId = Integer.parseInt(JwtCookieHandler.getDecodedFromContext(ctx).getIssuer());
      var sightings = sightingsService.getSightings(userId);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }

  public void getSightingsForGeo(Context ctx) {
    try {
      var userId = Integer.parseInt(JwtCookieHandler.getDecodedFromContext(ctx).getIssuer());
      var geoId = Integer.parseInt(ctx.pathParam("geo"));
      var sightings = sightingsService.getSightings(geoId, userId);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }
}
