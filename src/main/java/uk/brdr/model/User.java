package uk.brdr.model;

public class User {
  int id;
  String firstName;
  String lastName;
  String username;
  String password;
  String fullName;

  public User(int id, String firstName, String lastName, String username, String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.fullName = firstName + " " + lastName;
  }
}
