package uk.brdr.properties;

import com.typesafe.config.Config;
import java.net.URISyntaxException;

public class ApiProperties {

  private final DatabaseProperties databaseProperties;
  private final ServerProperties serverProperties;
  private final JwtProperties jwtProperties;
  private final MailServiceProperties mailServiceProperties;

  private ApiProperties(
      DatabaseProperties databaseProperties,
      ServerProperties serverProperties,
      JwtProperties jwtProperties,
      MailServiceProperties mailServiceProperties) {
    this.databaseProperties = databaseProperties;
    this.serverProperties = serverProperties;
    this.jwtProperties = jwtProperties;
    this.mailServiceProperties = mailServiceProperties;
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

  public MailServiceProperties getMailServiceProperties() {
    return mailServiceProperties;
  }

  public static ApiProperties fromConfig(Config config) {
    try {
      var database =
          getDatabasePropertiesForEnvironment(
              config.getString("environment"), config.getConfig("database"));
      var server = ServerProperties.fromConfig(config.getConfig("javalin"));
      var jwt = JwtProperties.fromConfig(config.getConfig("jwt"));
      var mail = MailServiceProperties.fromConfig(config.getConfig("mail"));
      return new ApiProperties(database, server, jwt, mail);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private static DatabaseProperties getDatabasePropertiesForEnvironment(
      String environment, Config databaseConfig) throws URISyntaxException {
    if (environment.equals("local")) {
      return DatabaseProperties.fromConfig(databaseConfig);
    }
    return DatabaseProperties.fromHerokuConfig(databaseConfig);
  }
}
