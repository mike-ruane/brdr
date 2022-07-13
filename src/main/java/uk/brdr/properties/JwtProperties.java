package uk.brdr.properties;

import com.typesafe.config.Config;

public class JwtProperties {

  private final String secret;

  public JwtProperties(String secret) {
    this.secret = secret;
  }

  public String getSecret() {
    return secret;
  }

  public static JwtProperties fromConfig(Config config) {
    return new JwtProperties(config.getString("secret"));
  }
}
