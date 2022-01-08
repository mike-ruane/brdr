package uk.brdr;

import com.typesafe.config.ConfigFactory;
import io.javalin.Javalin;
import org.flywaydb.core.Flyway;
import uk.brdr.data.DatabaseConfiguration;
import uk.brdr.data.SpeciesDaoImpl;
import uk.brdr.properties.ApiProperties;

public class Application {

  private static final int port = 8000;


  public static void main(String[] args) {
    var conf = ConfigFactory.load();
    var properties = ApiProperties.fromConfig(conf);
    var databaseConfiguration = new DatabaseConfiguration(properties.getDatabaseProperties());
    var datasource = databaseConfiguration.getDatasource();
    Flyway flyway = Flyway.configure().dataSource(datasource).load();
    flyway.migrate();
    var speciesDaoImpl = new SpeciesDaoImpl(datasource);
    Javalin app = Javalin.create().start(port);
    app.get("/species", ctx -> ctx.json(speciesDaoImpl.getAll()));
  }
}
