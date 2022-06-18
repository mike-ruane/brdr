package uk.brdr;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import uk.brdr.controllers.GeosController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.controllers.UserController;
import uk.brdr.handlers.JwtCookieHandler;
import uk.brdr.managers.TokenManager;

public class Application {

  private final Javalin app;

  public Application(
      TokenManager tokenManager,
      SightingController sightingController,
      SpeciesController speciesController,
      UserController userController,
      GeosController geosController) {
    app =
        Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .routes(() -> path("api", () -> {
              get("species", speciesController::getAll);
              get("geos", geosController::getGeoNames);
              path("sightings", () -> {
                post(sightingController::addSighting);
                get(sightingController::getSightingsByGeo);
                path("{geo}", () -> get(sightingController::getSightingsForGeo));
                  });
              post("register", userController::register);
              post("login", userController::login);
              get("user/validate", userController::validate);
            }));

    app.before("api/sightings", JwtCookieHandler.createCookieDecodeHandler(tokenManager));
    app.before("api/sightings/{geo}", JwtCookieHandler.createCookieDecodeHandler(tokenManager));
  }

  public Javalin javalinApp() {
    return app;
  }
}
