package uk.brdr.controllers;

import static uk.brdr.serializers.Utils.serializeSightings;

import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import java.util.Optional;
import uk.brdr.data.dao.UserDao;
import uk.brdr.handlers.JwtCookieHandler;
import uk.brdr.model.User;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.services.SightingsService;

public class SightingController {

  private final SightingsService sightingsService;
  private final UserDao userDao;

  public SightingController(SightingsService sightingsService, UserDao userDao) {
    this.sightingsService = sightingsService;
    this.userDao = userDao;
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
      var userId =
          Optional.ofNullable(ctx.queryParam("username"))
              .flatMap(username -> userDao.findByUsername(username).map(User::getId))
              .orElse(Integer.parseInt(JwtCookieHandler.getDecodedFromContext(ctx).getIssuer()));
      var sightingsByGeometry = sightingsService.getSightings(userId);
      ctx.json(serializeSightings(sightingsByGeometry));
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }

  public void getSightingsForGeo(Context ctx) {
    try {
      var userId = Integer.parseInt(JwtCookieHandler.getDecodedFromContext(ctx).getIssuer());
      var geoId = Integer.parseInt(ctx.pathParam("geo"));
      var sightings = sightingsService.getSightingsByOrder(geoId, userId);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }
}
