package uk.brdr.services;

import uk.brdr.model.User;

public interface UserService {

  void save(User user);

  User login(User user);

  String getUsername(int userId);
}
