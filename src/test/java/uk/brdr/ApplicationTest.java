package uk.brdr;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.testtools.TestUtil;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.controllers.UserController;
import uk.brdr.data.dao.SpeciesDao;
import uk.brdr.data.dao.SpeciesDaoImpl;
import uk.brdr.managers.JwtTokenManager;
import uk.brdr.managers.TokenManager;
import uk.brdr.model.User;
import uk.brdr.model.sighting.GeoSighting;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.services.SightingsService;
import uk.brdr.services.SightingsServiceImpl;
import uk.brdr.services.UserService;
import uk.brdr.services.UserServiceImpl;

public class ApplicationTest {

  private static final Algorithm algorithm = Algorithm.HMAC256("secret");
  SightingsService sightingsService = mock(SightingsServiceImpl.class);
  SpeciesDao speciesDao = mock(SpeciesDaoImpl.class);
  UserService userServiceImpl = mock(UserServiceImpl.class);
  TokenManager tokenManager = new JwtTokenManager(algorithm);

  SightingController sightingController = new SightingController(sightingsService);
  SpeciesController speciesController = new SpeciesController(speciesDao);
  UserController userController = new UserController(userServiceImpl);
  Javalin app =
      new Application(tokenManager, sightingController, speciesController, userController)
          .javalinApp();

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void addSighting() {
    var sighting = new Sighting(0, 123, List.of(123), 535, Date.valueOf("2022-01-29"));
    var body =
        "{\"userId\": 123, \"species\": [123], \"locationId\": \"535\", \"date\": \"2022-01-29\"}";
    var user = new User(1,"Mike", "mike@jruane.com", "secure-password");
    var jwt = tokenManager.issueToken(user);
    doNothing().when(sightingsService).addSighting(sighting);
    TestUtil.test(
        app,
        (server, client) ->
            assertThat(
                    client
                        .post("/api/sightings", body, req -> req.addHeader("Cookie", "jwt=" + jwt))
                        .code())
                .isEqualTo(201));
  }

  @Test
  public void unauthenticatedAddSighting() {
    var body =
        "{\"userId\": 123, \"species\": [123], \"locationId\": \"535\", \"date\": \"2022-01-29\"}";

    TestUtil.test(
        app,
        (server, client) -> assertThat(client.post("/api/sightings", body).code()).isEqualTo(401));
  }

  @Test
  public void getSightings() {
    var user = new User(1, "Mike", "mike@jruane.com", "secure-password");
    var jwt = tokenManager.issueToken(user);
    List<BigDecimal[]> geo = new ArrayList<BigDecimal[]>();
    geo.add(new BigDecimal[]{BigDecimal.valueOf(5109132, 5), BigDecimal.valueOf(5109132, 5)});
    var sightingsByLocation =
        List.of(
            new GeoSighting(
                "Abbots Worthy",
                geo,
                List.of(1, 2)
                )
            );
    when(sightingsService.getSightingsForUser(1))
        .thenReturn(sightingsByLocation);
    TestUtil.test(
        app,
        (server, client) ->
            assertThat(
                    client
                        .get("/api/sightings", req -> req.addHeader("Cookie", "jwt=" + jwt))
                        .body()
                        .string())
                .isEqualTo(objectMapper.writeValueAsString(sightingsByLocation)));
  }

  @Test
  public void unauthenticatedGetSighting() {
    TestUtil.test(
        app, (server, client) -> assertThat(client.get("/api/sightings").code()).isEqualTo(401));
  }
}
