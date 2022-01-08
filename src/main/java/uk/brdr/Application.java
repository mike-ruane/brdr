package uk.brdr;

import io.javalin.Javalin;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import uk.brdr.data.SpeciesDaoImpl;

public class Application {

  private static final int port = 8000;


  public static void main(String[] args) {
    Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/brdr", "mruane", "").load();
    flyway.migrate();
    var speciesDaoImpl = new SpeciesDaoImpl(getDatasource());
    Javalin app = Javalin.create().start(port);
    app.get("/species", ctx -> ctx.json(speciesDaoImpl.getAll()));
  }

  public static DataSource getDatasource() {
    var datasource = new PGSimpleDataSource();
    datasource.setUser("mruane");
    datasource.setPassword("");
    datasource.setUrl("jdbc:postgresql://localhost/brdr");
    return datasource;
  }
}
