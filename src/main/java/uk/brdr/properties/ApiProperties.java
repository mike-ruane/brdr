package uk.brdr.properties;

import com.typesafe.config.Config;
import java.net.URISyntaxException;

public class ApiProperties {

  private final DatabaseProperties databaseProperties;
  private final ServerProperties serverProperties;

  private ApiProperties(DatabaseProperties databaseProperties, ServerProperties serverProperties) {
    this.databaseProperties = databaseProperties;
    this.serverProperties = serverProperties;
  }

  public DatabaseProperties getDatabaseProperties() {
    return databaseProperties;
  }

  public ServerProperties getServerProperties() {
    return serverProperties;
  }

  public static ApiProperties fromConfig(Config config) {
    var databaseConfig = config.getConfig("database");
    var serverConfig = config.getConfig("javalin");
    var environment = config.getString("environment");

    if (environment.equals("local")) {
      var database = DatabaseProperties.fromConfig(databaseConfig);
      var server = ServerProperties.fromConfig(serverConfig);
      return new ApiProperties(database, server);
    }

    try {
      var database = DatabaseProperties.fromHerokuConfig(databaseConfig);
      var server = ServerProperties.fromConfig(serverConfig);
      return new ApiProperties(database, server);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
