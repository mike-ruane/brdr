package uk.brdr.properties;

import com.typesafe.config.Config;
import java.net.URISyntaxException;

public class ApiProperties {

  private final DatabaseProperties databaseProperties;
  private final ServerProperties serverProperties;
  private final JwtProperties jwtProperties;

  private ApiProperties(DatabaseProperties databaseProperties, ServerProperties serverProperties, JwtProperties jwtProperties) {
    this.databaseProperties = databaseProperties;
    this.serverProperties = serverProperties;
    this.jwtProperties = jwtProperties;
  }

  public DatabaseProperties getDatabaseProperties() {
    return databaseProperties;
  }

  public ServerProperties getServerProperties() {
    return serverProperties;
  }

  public JwtProperties getJwtProperties() {
    return jwtProperties;
  }

  public static ApiProperties fromConfig(Config config) {
    var databaseConfig = config.getConfig("database");
    var serverConfig = config.getConfig("javalin");
    var jwtConfig = config.getConfig("jwt");
    var environment = config.getString("environment");

    if (environment.equals("local")) {
      var database = DatabaseProperties.fromConfig(databaseConfig);
      var server = ServerProperties.fromConfig(serverConfig);
      var jwt = JwtProperties.fromConfig(jwtConfig);
      return new ApiProperties(database, server, jwt);
    }

    try {
      var database = DatabaseProperties.fromHerokuConfig(databaseConfig);
      var server = ServerProperties.fromConfig(serverConfig);
      var jwt = JwtProperties.fromConfig(jwtConfig);
      return new ApiProperties(database, server, jwt);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
