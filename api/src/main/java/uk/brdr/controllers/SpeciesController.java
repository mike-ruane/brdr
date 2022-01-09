package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import uk.brdr.data.SpeciesDaoImpl;

public class SpeciesController {

  private final SpeciesDaoImpl speciesDaoImpl;

  public SpeciesController(SpeciesDaoImpl speciesDaoImpl) {
    this.speciesDaoImpl = speciesDaoImpl;
  }

  public void getAll(Context ctx) {
    try {
      var species = speciesDaoImpl.getAll();
      ctx.json(species);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
