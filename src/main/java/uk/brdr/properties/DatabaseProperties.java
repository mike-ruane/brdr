package uk.brdr.properties;

import com.typesafe.config.Config;
import java.net.URI;
import java.net.URISyntaxException;

public class DatabaseProperties {

  private final String url;
  private final String user;
  private final String password;

  private DatabaseProperties(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public String getUrl() {
    return url;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public static DatabaseProperties fromConfig(Config config) {
    return new DatabaseProperties(
        config.getString("url"), config.getString("user"), config.getString("password"));
  }

  public static DatabaseProperties fromHerokuConfig(Config config) throws URISyntaxException {
    var uri = new URI(config.getString("url"));
    var user = uri.getUserInfo().split(":")[0];
    var password = uri.getUserInfo().split(":")[1];
    var url = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + ":" + uri.getPath() + "?sslmode=require";

    return new DatabaseProperties(url, user, password);
  }

  public static DatabaseProperties forTesting(String host, String user, String password) {
    return new DatabaseProperties(host, user, password);
  }
}
