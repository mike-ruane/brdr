package uk.brdr.model;

import java.util.Objects;

public class User {

  public int id;
  public String email;
  public String password;

  public User(int id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public User() {
  }

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean comparePassword(String password) {
    return this.password.equals(password);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, password);
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
