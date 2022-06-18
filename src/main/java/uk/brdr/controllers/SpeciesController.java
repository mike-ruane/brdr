package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
      var species = speciesDao.getAll();
      ctx.json(species);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }

  public void getSpecies(Context ctx) {
    try {
      var batch = ctx.queryParam("ids");
      assert batch != null;
      var speciesIds =
          Stream.of(batch.split(",", -1)).map(Integer::parseInt).collect(Collectors.toList());
      var species = speciesDao.getSpecies(speciesIds);
      ctx.json(species);
    } catch (RuntimeException e) {
      throw new ServiceUnavailableResponse();
    }
  }
}
