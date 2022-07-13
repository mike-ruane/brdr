package uk.brdr.properties;

import com.typesafe.config.Config;

public class ServerProperties {

  private final int port;

  private ServerProperties(int port) {
    this.port = port;
  }

  public int getPort() {
    return port;
  }

  public static ServerProperties fromConfig(Config config) {
    return new ServerProperties(config.getInt("port"));
  }
}
