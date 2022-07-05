package uk.brdr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.InternalServerErrorResponse;
import java.util.List;
import uk.brdr.handlers.JwtCookieHandler;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingByGeometryList;
import uk.brdr.model.sighting.SightingsByGeometry;
import uk.brdr.services.SightingsService;

public class SightingController {

  private final SightingsService sightingsService;
  private static ObjectMapper objectMapper = new ObjectMapper();

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
      var sightings = sightingsService.getSightings(geoId, userId);
      ctx.json(sightings);
    } catch (RuntimeException e) {
      throw new InternalServerErrorResponse("");
    }
  }

  private String serializeSightings(List<SightingsByGeometry> sightings) {
    var serializable = new SightingByGeometryList(sightings);
    try {
      objectMapper.writeValueAsString(serializable);
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorResponse("unable to serialize to geojson");
    }
  }
}
