import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import uk.brdr.Application;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.controllers.UserController;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.data.daoimpl.LocationsDaoImpl;
import uk.brdr.data.daoimpl.SightingsDaoImpl;
import uk.brdr.data.daoimpl.SpeciesDaoImpl;
import uk.brdr.data.daoimpl.UserDaoImpl;
import uk.brdr.data.repositories.SightingsOverviewImpl;
import uk.brdr.properties.ApiProperties;
import uk.brdr.services.UserService;

public class Main {

  private static final int port = 8000;

  public static void main(String[] args) {
    var conf = ConfigFactory.load();
    var properties = ApiProperties.fromConfig(conf);
    var databaseConfiguration = new DatabaseConfiguration(properties.getDatabaseProperties());
    var datasource = databaseConfiguration.getDatasource();
    Flyway flyway = Flyway.configure().dataSource(datasource).load();
    flyway.migrate();

    var speciesDaoImpl = new SpeciesDaoImpl(datasource);
    var sightingsDaoImpl = new SightingsDaoImpl(datasource);
    var locationsDaoImpl = new LocationsDaoImpl(datasource);
    var userDaoImpl = new UserDaoImpl(datasource);

    var userService = new UserService(userDaoImpl);
    var sightingsOverviewImpl = new SightingsOverviewImpl(sightingsDaoImpl, locationsDaoImpl);
    var speciesController = new SpeciesController(speciesDaoImpl);
    var sightingsController = new SightingController(sightingsDaoImpl, sightingsOverviewImpl);
    var locationsController = new LocationsController(locationsDaoImpl);
    var userController = new UserController(userService);

    var app = new Application(speciesController, sightingsController, locationsController, userController);
    app.javalinApp().start(port);
  }
}
