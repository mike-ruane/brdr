package uk.brdr.model;

import java.util.Objects;

public class Mail {

  private String username;
  private String body;

  public Mail() {}

  public String getUsername() {
    return username;
  }

  public String getBody() {
    return body;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Mail mail = (Mail) o;
    return Objects.equals(username, mail.username) && Objects.equals(body,
        mail.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, body);
  }

  @Override
  public String toString() {
    return "Mail{" +
        "username='" + username + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}
