package uk.brdr.properties;

import com.typesafe.config.Config;

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
    var database = DatabaseProperties.fromConfig(databaseConfig);
    return new ApiProperties(database);
  }
}
