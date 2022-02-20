package uk.brdr.services;

import uk.brdr.model.User;

public interface UserService {

  void save(User user);

  String login(User user);
}
