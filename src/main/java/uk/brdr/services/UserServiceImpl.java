package uk.brdr.services;

import io.javalin.http.BadRequestResponse;
import uk.brdr.data.dao.UserDao;
import uk.brdr.managers.TokenManager;
import uk.brdr.model.User;

public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final TokenManager tokenManager;

  public UserServiceImpl(UserDao userDao, TokenManager tokenManager) {
    this.userDao = userDao;
    this.tokenManager = tokenManager;
  }

  public void save(User user) {
    userDao.findByEmail(user.getEmail()).ifPresent(s -> { throw new BadRequestResponse(); });
    userDao.addUser(user);
  }

  public String login(User user) {
    var maybeUser = userDao.findByEmail(user.getEmail());
    if (maybeUser.isEmpty()) {
      throw new BadRequestResponse();
    }
    var dbUser = maybeUser.get();
    if (!dbUser.comparePassword(user.getPassword())) {
      throw new BadRequestResponse();
    }

    return tokenManager.issueToken(dbUser);
  }
}
