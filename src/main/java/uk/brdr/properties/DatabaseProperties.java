package uk.brdr.properties;

import com.typesafe.config.Config;

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
    return new DatabaseProperties(config.getString("url"), config.getString("user"), config.getString("password"));
  }

}
