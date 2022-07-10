package uk.brdr.properties;

import com.typesafe.config.Config;
import java.net.URISyntaxException;

public class ApiProperties {

  private final DatabaseProperties databaseProperties;

  private ApiProperties(DatabaseProperties databaseProperties) {
    this.databaseProperties = databaseProperties;
  }

  public DatabaseProperties getDatabaseProperties() {
    return databaseProperties;
  }

  public static ApiProperties fromConfig(Config config) {
    var databaseConfig = config.getConfig("database");
    try {
      var database = DatabaseProperties.fromHerokuConfig(databaseConfig);
      return new ApiProperties(database);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
