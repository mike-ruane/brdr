package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import uk.brdr.data.SpeciesDao;

public class SpeciesController {

  private final SpeciesDao speciesDao;

  public SpeciesController(SpeciesDao speciesDao) {
    this.speciesDao = speciesDao;
  }

  public void getAll(Context ctx) {
    try {
      var species = speciesDao.getAll();
      ctx.json(species);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
