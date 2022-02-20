package uk.brdr.services;

import java.util.Optional;
import uk.brdr.data.dao.UserDao;
import uk.brdr.model.User;

public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public void save(User user) {
    userDao.addUser(user);
  }

  public Optional<User> findByEmail(String email) {
    return userDao.findByEmail(email);
  }
}
