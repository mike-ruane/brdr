package uk.brdr;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;

public class Application {

  private final Javalin app;

  public Application(
      SpeciesController speciesController,
      SightingController sightingController,
      LocationsController locationsController) {
    app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
        .routes(() -> {
          path("v1/species", () ->
              get(speciesController::getAll));
          path("v1/sightings", () ->
              post(sightingController::addSighting));
          path("v1/locations", () ->
              get(locationsController::getLocations));
        });
  }

  public Javalin javalinApp() {
    return app;
  }

}
