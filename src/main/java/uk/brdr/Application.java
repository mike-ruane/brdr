package uk.brdr;

import io.javalin.Javalin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.flywaydb.core.Flyway;
import uk.brdr.data.SpeciesRepository;
import uk.brdr.data.SpeciesRepositoryImpl;

public class Application {

  private static final int port = 8000;


  public static void main(String[] args) {
    Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/brdr", "mruane", "").load();
    flyway.migrate();
    String url = "jdbc:postgresql://localhost/brdr";
    Properties props = new Properties();
    props.setProperty("user","mruane");
    props.setProperty("password","");
    try {
      Connection conn = DriverManager.getConnection(url, props);
      SpeciesRepository repository = new SpeciesRepositoryImpl(conn);
      Javalin app = Javalin.create().start(port);
      app.get("/", ctx -> ctx.result("Hi Jo!"));
      app.get("/species", ctx -> ctx.json(repository.getSpecies()));
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
