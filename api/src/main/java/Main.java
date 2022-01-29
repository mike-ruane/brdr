import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import uk.brdr.Application;
import uk.brdr.controllers.LocationsController;
import uk.brdr.controllers.SightingController;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.data.daoimpl.LocationsDaoImpl;
import uk.brdr.data.daoimpl.SightingsDaoImpl;
import uk.brdr.data.daoimpl.SpeciesDaoImpl;
import uk.brdr.properties.ApiProperties;

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
    var speciesController = new SpeciesController(speciesDaoImpl);
    var sightingsController = new SightingController(sightingsDaoImpl);
    var locationsController = new LocationsController(locationsDaoImpl);
    var app = new Application(speciesController, sightingsController, locationsController);
    app.javalinApp().start(port);
  }

}
