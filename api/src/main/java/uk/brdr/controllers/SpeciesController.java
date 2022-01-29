package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.dao.SpeciesDao;

public class SpeciesController {

  private final SpeciesDao speciesDao;

  Logger logger = LoggerFactory.getLogger(SpeciesController.class);

  public SpeciesController(SpeciesDao speciesDao) {
    this.speciesDao = speciesDao;
  }

  public void getAll(Context ctx) {
    try {
      logger.info("yep I'm here");
      var species = speciesDao.getAll();
      ctx.json(species);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
