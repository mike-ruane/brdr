package uk.brdr;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.controllers.UserController;

public class Application {

  private final Javalin app;

  public Application(
      SpeciesController speciesController,
      SightingController sightingController,
      LocationsController locationsController,
      UserController userController) {
    app =
        Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .routes(
                () -> {
                  path("v1/species", () -> get(speciesController::getAll));
                  path(
                      "v1/sightings",
                      () -> {
                        post(sightingController::addSighting);
                        path("{userId}", () -> get(sightingController::getSightingsOverview));
                      });
                  path(
                      "v1/locations/{countyId}",
                      () -> get(locationsController::getLocationsByCounty));
                  path("v1/counties", () -> get(locationsController::getCounties));
                  path("v1/login", () -> post(userController::login));
                  path("v1/user/validate", () -> get(userController::validate));
                });
  }

  public Javalin javalinApp() {
    return app;
  }
}
