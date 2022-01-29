package uk.brdr;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import io.javalin.testtools.TestUtil;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.data.LocationsDaoImpl;
import uk.brdr.data.SightingsDaoImpl;
import uk.brdr.data.SpeciesDaoImpl;
import uk.brdr.model.Sighting;
import uk.brdr.model.Species;

public class ApplicationTest {

  SpeciesDaoImpl speciesDao = mock(SpeciesDaoImpl.class);
  SightingsDaoImpl sightingsDao = mock(SightingsDaoImpl.class);
  LocationsDaoImpl locationsDao = mock(LocationsDaoImpl.class);
  SpeciesController speciesController = new SpeciesController(speciesDao);
  SightingController sightingController = new SightingController(sightingsDao);
  LocationsController locationsController = new LocationsController(locationsDao);
  Javalin app = new Application(speciesController, sightingController, locationsController).javalinApp();

  @Test
  public void getAllSpecies() throws IOException {
    var speciesJson = getClass().getClassLoader().getResourceAsStream("species.json");
    List<Species> species = new ObjectMapper().readValue(speciesJson, new TypeReference<List<Species>>() {});
    var expected = new JavalinJackson().toJsonString(species);
    when(speciesDao.getAll()).thenReturn(species);
    TestUtil.test(app, (server, client) -> {
      assertThat(client.get("/v1/species").code()).isEqualTo(200);
      assertThat(client.get("/v1/species").body().string()).isEqualTo(expected);
    });
  }

  @Test
  public void returnsServerErrorWhenDaoFails() {
    when(speciesDao.getAll()).thenThrow(new RuntimeException());
    TestUtil.test(app, (server, client) -> {
      assertThat(client.get("/v1/species").code()).isEqualTo(503);
      assertThat(client.get("/v1/species").body().string()).isEqualTo("Service unavailable");
    });
  }

  @Test
  public void addSighting() {
    var sighting = new Sighting(0, 123, 1, "2022-01-29");
    var body = "{\"userId\": 123, \"speciesId\": 123, \"city\": \"London\"}";
    doNothing().when(sightingsDao).addSighting(sighting);
    TestUtil.test(app, (server, client) -> 
      assertThat(client.post("/v1/sightings", body).code()).isEqualTo(201));
  }
}
