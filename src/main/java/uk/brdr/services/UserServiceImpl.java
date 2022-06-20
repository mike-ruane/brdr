package uk.brdr.services;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import uk.brdr.data.dao.UserDao;
import uk.brdr.model.User;

public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  public void save(User user) {
    userDao
        .findByEmail(user.getEmail())
        .ifPresent(
            s -> {
              throw new BadRequestResponse();
            });
    userDao.addUser(user);
  }

  public User login(User user) {
    var maybeUser = userDao.findByEmail(user.getEmail());
    if (maybeUser.isEmpty()) {
      throw new BadRequestResponse();
    }
    var dbUser = maybeUser.get();
    if (!dbUser.comparePassword(user.getPassword())) {
      throw new BadRequestResponse();
    }
    return dbUser;
  }

  @Override
  public String getUsername(int userId) {
    return userDao.findById(userId).map(User::getUsername).orElseThrow(NotFoundResponse::new);
  }
}
