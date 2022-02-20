package uk.brdr.controllers;

import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import java.util.Optional;
import uk.brdr.handlers.JwtCookieHandler;
import uk.brdr.model.location.LocationType;
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
      sightingsService.addSighting(sighting);
      ctx.status(HttpCode.CREATED);
    } catch (IllegalStateException e) {
      throw new ConflictResponse();
    } catch (Exception e) {
      throw new InternalServerErrorResponse();
    }
  }

  public void getSightings(Context ctx) {
    try {
      var userId = Integer.parseInt(JwtCookieHandler.getDecodedFromContext(ctx).getIssuer());
      var locationType = getLocationTypeQueryParam(ctx);
      var sightings = sightingsService.getSightingsForUserByLocation(userId, locationType);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }

  private LocationType getLocationTypeQueryParam(Context ctx) {
    return LocationType.get(
        Optional.ofNullable(ctx.queryParam("locationType")).orElse("locations"));
  }
}
