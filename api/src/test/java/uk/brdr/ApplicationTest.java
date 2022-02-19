package uk.brdr;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import io.javalin.testtools.TestUtil;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.data.daoimpl.LocationsDaoImpl;
import uk.brdr.data.daoimpl.SightingsDaoImpl;
import uk.brdr.data.daoimpl.SpeciesDaoImpl;
import uk.brdr.data.repositories.SightingsOverviewImpl;
import uk.brdr.model.Species;
import uk.brdr.model.sighting.Sighting;

public class ApplicationTest {

  SpeciesDaoImpl speciesDao = mock(SpeciesDaoImpl.class);
  SightingsDaoImpl sightingsDao = mock(SightingsDaoImpl.class);
  SightingsOverviewImpl sightingsOverview = mock(SightingsOverviewImpl.class);
  LocationsDaoImpl locationsDao = mock(LocationsDaoImpl.class);
  SpeciesController speciesController = new SpeciesController(speciesDao);
  SightingController sightingController = new SightingController(sightingsDao, sightingsOverview);
  LocationsController locationsController = new LocationsController(locationsDao);
  Javalin app =
      new Application(speciesController, sightingController, locationsController).javalinApp();

  @Test
  public void getAllSpecies() throws IOException {
    var speciesJson = getClass().getClassLoader().getResourceAsStream("species.json");
    List<Species> species =
        new ObjectMapper().readValue(speciesJson, new TypeReference<List<Species>>() {});
    var expected = new JavalinJackson().toJsonString(species);
    when(speciesDao.getAll()).thenReturn(species);
    TestUtil.test(
        app,
        (server, client) -> {
          assertThat(client.get("/v1/species").code()).isEqualTo(200);
          assertThat(client.get("/v1/species").body().string()).isEqualTo(expected);
        });
  }

  @Test
  public void returnsServerErrorWhenDaoFails() {
    when(speciesDao.getAll()).thenThrow(new RuntimeException());
    TestUtil.test(
        app,
        (server, client) -> {
          assertThat(client.get("/v1/species").code()).isEqualTo(503);
          assertThat(client.get("/v1/species").body().string()).isEqualTo("Service unavailable");
        });
  }

  @Test
  public void addSighting() {
    var sighting = new Sighting(0, 123, List.of(123), 535, Date.valueOf("2022-01-29"));
    var body =
        "{\"userId\": 123, \"species\": [123], \"locationId\": \"535\", \"date\": \"2022-01-29\"}";
    doNothing().when(sightingsDao).addSighting(sighting);
    TestUtil.test(
        app,
        (server, client) -> assertThat(client.post("/v1/sightings", body).code()).isEqualTo(201));
  }
}
