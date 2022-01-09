package uk.brdr;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import uk.brdr.controllers.SpeciesController;

public class Application {

  private final Javalin app;

  public Application(SpeciesController speciesController) {
    app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
        .routes(() ->
            path("v1/species",() -> get(speciesController::getAll)));
  }

  public Javalin javalinApp() {
    return app;
  }

}
