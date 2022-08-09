package uk.brdr;

import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import uk.brdr.controllers.AdminController;
import uk.brdr.controllers.GeosController;
import uk.brdr.controllers.HealthCheckController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.controllers.UserController;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.data.dao.GeoLocationsDaoImpl;
import uk.brdr.data.dao.SightingsDaoImpl;
import uk.brdr.data.dao.SpeciesDaoImpl;
import uk.brdr.data.dao.UserDaoImpl;
import uk.brdr.managers.JwtTokenManager;
import uk.brdr.properties.ApiProperties;
import uk.brdr.services.MailService;
import uk.brdr.services.SightingsServiceImpl;
import uk.brdr.services.UserServiceImpl;
import uk.brdr.utils.HashingUtils;

public class Main {

  public static void main(String[] args) {
    var conf = ConfigFactory.load();
    var properties = ApiProperties.fromConfig(conf.getConfig("application"));
    var databaseConfiguration = new DatabaseConfiguration(properties.getDatabaseProperties());
    var datasource = databaseConfiguration.getDatasource();
    Flyway flyway = Flyway.configure().dataSource(datasource).load();
    flyway.baseline();
    flyway.migrate();

    // DAO
    var sightingsDaoImpl = new SightingsDaoImpl(datasource);
    var geoLocationsDaoImpl = new GeoLocationsDaoImpl(datasource);
    var speciesDaoImpl = new SpeciesDaoImpl(datasource);
    var userDaoImpl = new UserDaoImpl(datasource);

    var tokenManager = new JwtTokenManager(properties.getJwtProperties());

    // services
    var sightingsService = new SightingsServiceImpl(sightingsDaoImpl, geoLocationsDaoImpl);
    var userService = new UserServiceImpl(userDaoImpl, new HashingUtils());
    var mailService = new MailService(properties.getMailServiceProperties(), userDaoImpl);

    // controllers
    var healthCheckController = new HealthCheckController();
    var sightingsController = new SightingController(sightingsService);
    var speciesController = new SpeciesController(speciesDaoImpl);
    var userController = new UserController(userService, tokenManager);
    var geosController = new GeosController(geoLocationsDaoImpl);
    var adminController = new AdminController(mailService);

    var app = new Application(
        tokenManager,
        healthCheckController,
        adminController,
        sightingsController,
        speciesController,
        userController,
        geosController);

    app.javalinApp().start(properties.getServerProperties().getPort());
  }
}
