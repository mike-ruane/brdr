package uk.brdr.controllers;

import static uk.brdr.serializers.Utils.serializeSightings;

import io.javalin.http.BadRequestResponse;
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
  private final JwtCookieHandler jwtCookieHandler;

  public SightingController(
      SightingsService sightingsService, UserDao userDao, JwtCookieHandler jwtCookieHandler) {
    this.sightingsService = sightingsService;
    this.userDao = userDao;
    this.jwtCookieHandler = jwtCookieHandler;
  }

  public void addSighting(Context ctx) {
    var decodedJWT = jwtCookieHandler.verifyJwtTokenFromContext(ctx);
    try {
      var sighting = ctx.bodyAsClass(Sighting.class);
      var userId = jwtCookieHandler.getUserIdFromJWT(decodedJWT);
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
      var userId = getUserIdFromContext(ctx);
      var sightingsByGeometry = sightingsService.getSightings(userId);
      ctx.json(serializeSightings(sightingsByGeometry));
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }

  public void getSightingsForGeo(Context ctx) {
    try {
      var userId = getUserIdFromContext(ctx);
      var geoId = Integer.parseInt(ctx.pathParam("geo"));
      var sightings = sightingsService.getSightingsByOrder(geoId, userId);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }

  private Integer getUserIdFromContext(Context ctx) {
    return Optional.ofNullable(ctx.queryParam("username"))
        .flatMap(username -> userDao.findByUsername(username).map(User::getId))
        .orElseThrow(BadRequestResponse::new);
  }
}
