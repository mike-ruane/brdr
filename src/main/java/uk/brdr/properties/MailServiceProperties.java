package uk.brdr.properties;

import com.typesafe.config.Config;
import java.util.Properties;

public class MailServiceProperties {

  private final Properties properties;
  private final String username;
  private final String password;

  private MailServiceProperties(Properties properties, String username, String password) {
    this.properties = properties;
    this.username = username;
    this.password = password;
  }

  public Properties getProperties() {
    return properties;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public static MailServiceProperties fromConfig(Config config) {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", true);
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", config.getString("host"));
    properties.put("mail.smtp.port", config.getString("port"));
    properties.put("mail.smtp.ssl.trust", config.getString("host"));

    return new MailServiceProperties(
        properties, config.getString("username"), config.getString("password"));
  }
}
