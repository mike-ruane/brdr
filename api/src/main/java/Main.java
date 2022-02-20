import com.auth0.jwt.algorithms.Algorithm;
import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import uk.brdr.Application;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.UserController;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.data.dao.LocationsDaoImpl;
import uk.brdr.data.dao.SightingsDaoImpl;
import uk.brdr.data.dao.UserDaoImpl;
import uk.brdr.managers.JwtTokenManager;
import uk.brdr.properties.ApiProperties;
import uk.brdr.services.SightingsServiceImpl;
import uk.brdr.services.UserServiceImpl;

public class Main {

  private static final int port = 8000;

  public static void main(String[] args) {
    var conf = ConfigFactory.load();
    var properties = ApiProperties.fromConfig(conf);
    var databaseConfiguration = new DatabaseConfiguration(properties.getDatabaseProperties());
    var datasource = databaseConfiguration.getDatasource();
    Flyway flyway = Flyway.configure().dataSource(datasource).load();
    flyway.migrate();
    var algorithm = Algorithm.HMAC256("secret");

    // DAO
    var sightingsDaoImpl = new SightingsDaoImpl(datasource);
    var locationsDaoImpl = new LocationsDaoImpl(datasource);
    var userDaoImpl = new UserDaoImpl(datasource);

    var tokenManager = new JwtTokenManager(algorithm);

    // services
    var sightingsService = new SightingsServiceImpl(sightingsDaoImpl, locationsDaoImpl);
    var userService = new UserServiceImpl(userDaoImpl, tokenManager);

    // controllers
    var sightingsController = new SightingController(sightingsService);
    var locationsController = new LocationsController(locationsDaoImpl);
    var userController = new UserController(userService);

    var app =
        new Application(tokenManager, sightingsController, locationsController, userController);
    app.javalinApp().start(port);
  }
}
