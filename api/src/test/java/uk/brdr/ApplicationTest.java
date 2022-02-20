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
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.UserController;
import uk.brdr.data.dao.LocationsDaoImpl;
import uk.brdr.managers.JwtTokenManager;
import uk.brdr.managers.TokenManager;
import uk.brdr.model.User;
import uk.brdr.model.location.LocationType;
import uk.brdr.model.sighting.Sighting;
import uk.brdr.model.sighting.SightingByLocation;
import uk.brdr.model.sighting.SightingOverview;
import uk.brdr.services.SightingsServiceImpl;
import uk.brdr.services.UserServiceImpl;

public class ApplicationTest {

  private static final Algorithm algorithm = Algorithm.HMAC256("secret");
  SightingsServiceImpl sightingsService = mock(SightingsServiceImpl.class);
  LocationsDaoImpl locationsDao = mock(LocationsDaoImpl.class);
  UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
  TokenManager tokenManager = new JwtTokenManager(algorithm);

  SightingController sightingController = new SightingController(sightingsService);
  LocationsController locationsController = new LocationsController(locationsDao);
  UserController userController = new UserController(userServiceImpl);
  Javalin app =
      new Application(tokenManager, sightingController, locationsController, userController)
          .javalinApp();

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void addSighting() {
    var sighting = new Sighting(0, 123, List.of(123), 535, Date.valueOf("2022-01-29"));
    var body =
        "{\"userId\": 123, \"species\": [123], \"locationId\": \"535\", \"date\": \"2022-01-29\"}";
    var user = new User(1, "mike@jruane.com", "secure-password");
    var jwt = tokenManager.issueToken(user);
    doNothing().when(sightingsService).addSighting(sighting);
    TestUtil.test(
        app,
        (server, client) ->
            assertThat(
                    client
                        .post("/v1/sightings", body, req -> req.addHeader("Cookie", "jwt=" + jwt))
                        .code())
                .isEqualTo(201));
  }

  @Test
  public void unauthenticatedAddSighting() {
    var body =
        "{\"userId\": 123, \"species\": [123], \"locationId\": \"535\", \"date\": \"2022-01-29\"}";

    TestUtil.test(
        app,
        (server, client) -> assertThat(client.post("/v1/sightings", body).code()).isEqualTo(401));
  }

  @Test
  public void getSightings() {
    var user = new User(1, "mike@jruane.com", "secure-password");
    var jwt = tokenManager.issueToken(user);
    var sightingsByLocation =
        List.of(
            new SightingByLocation(
                "Abbots Worthy",
                BigDecimal.valueOf(5109132, 5),
                BigDecimal.valueOf(-129097, 5),
                List.of(
                    new SightingOverview(
                        56,
                        29,
                        1,
                        1,
                        64,
                        "Greenish Warbler",
                        "Phylloscopus",
                        Date.valueOf("2022-02-15")),
                    new SightingOverview(
                        56, 29, 1, 1, 13, "Pine Grosbeak", "Pinicola", Date.valueOf("2022-02-15")),
                    new SightingOverview(
                        56,
                        29,
                        1,
                        1,
                        78,
                        "Philadelphia Vireo",
                        "Vireo",
                        Date.valueOf("2022-02-15")))),
            new SightingByLocation(
                "Abbess Roding",
                BigDecimal.valueOf(5177815, 5),
                BigDecimal.valueOf(27685, 5),
                List.of(
                    new SightingOverview(
                        8, 4, 4, 1, 23, "Greenfinch", "Chloris", Date.valueOf("2022-02-15")),
                    new SightingOverview(
                        8, 4, 4, 1, 12, "Chaffinch", "Fringilla", Date.valueOf("2022-02-15")),
                    new SightingOverview(
                        8,
                        4,
                        4,
                        1,
                        5,
                        "Pallas's Grasshopper Warbler",
                        "Helopsaltes",
                        Date.valueOf("2022-02-15")),
                    new SightingOverview(
                        8,
                        4,
                        4,
                        1,
                        45,
                        "Rose-breasted Grosbeak",
                        "Pheucticus",
                        Date.valueOf("2022-02-15")))));
    when(sightingsService.getSightingsForUserByLocation(1, LocationType.LOCATIONS))
        .thenReturn(sightingsByLocation);
    TestUtil.test(
        app,
        (server, client) ->
            assertThat(
                    client
                        .get("/v1/sightings", req -> req.addHeader("Cookie", "jwt=" + jwt))
                        .body()
                        .string())
                .isEqualTo(objectMapper.writeValueAsString(sightingsByLocation)));
  }

  @Test
  public void unauthenticatedGetSighting() {
    TestUtil.test(
        app, (server, client) -> assertThat(client.get("/v1/sightings").code()).isEqualTo(401));
  }
}
