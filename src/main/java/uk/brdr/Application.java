package uk.brdr;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;

import io.javalin.Javalin;
import uk.brdr.controllers.SpeciesController;

public class Application {

  private final Javalin app;

  public Application(SpeciesController speciesController) {
    app = Javalin.create().routes(() ->
        path("v1/species",() -> get(speciesController::getAll)));
  }

  public Javalin javalinApp() {
    return app;
  }

}
