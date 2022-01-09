import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import uk.brdr.Application;
import uk.brdr.controllers.SpeciesController;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.data.SpeciesDaoImpl;
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
    var speciesController = new SpeciesController(speciesDaoImpl);
    var app = new Application(speciesController);
    app.javalinApp().start(port);
  }

}
