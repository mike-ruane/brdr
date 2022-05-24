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
    userDao.addUser(user);
  }

  public String login(User request) {
    var maybeUser = userDao.findByEmail(request.getEmail());
    if (maybeUser.isEmpty()) {
      throw new BadRequestResponse();
    }
    var user = maybeUser.get();
    if (!request.comparePassword(user.getPassword())) {
      throw new BadRequestResponse();
    }

    return tokenManager.issueToken(user);
  }
}
