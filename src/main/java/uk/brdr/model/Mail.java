package uk.brdr.model;

import java.util.Objects;

public class Mail {

  private String username;
  private String email;
  private String type;
  private String body;

  public Mail() {}

  public String getUsername() {
    return username;
  }

  public String getBody() {
    return body;
  }

  public String getEmail() {
    return email;
  }

  public String getType() {
    return type;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setType(String type) {
    this.type = type;
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
    return Objects.equals(username, mail.username)
        && Objects.equals(email, mail.email)
        && Objects.equals(type, mail.type)
        && Objects.equals(body, mail.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, email, type, body);
  }

  @Override
  public String toString() {
    return "Mail{"
        + "username='"
        + username
        + '\''
        + ", email='"
        + email
        + '\''
        + ", type='"
        + type
        + '\''
        + ", body='"
        + body
        + '\''
        + '}';
  }
}
