package uk.brdr.services;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import uk.brdr.data.dao.UserDao;
import uk.brdr.model.User;
import uk.brdr.utils.HashingUtils;

public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final HashingUtils hashingUtils;

  public UserServiceImpl(UserDao userDao, HashingUtils hashingUtils) {
    this.userDao = userDao;
    this.hashingUtils = hashingUtils;
  }

  public void save(User user) {
    userDao
        .findByEmail(user.getEmail())
        .ifPresent(
            s -> {
              throw new BadRequestResponse();
            });

    var hashedUser = hashingUtils.hashUserPassword(user);
    userDao.addUser(hashedUser);
  }

  public User login(User user) {
    var maybeUser = userDao.findByEmail(user.getEmail());
    if (maybeUser.isEmpty()) {
      throw new IllegalArgumentException();
    }
    var dbUser = maybeUser.get();
    if (!hashingUtils.validateUser(dbUser, user)) {
      throw new BadRequestResponse();
    }
    return dbUser;
  }

  @Override
  public String getUsername(int userId) {
    return userDao.findById(userId).map(User::getUsername).orElseThrow(NotFoundResponse::new);
  }
}
