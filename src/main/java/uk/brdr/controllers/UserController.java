package uk.brdr.controllers;

import com.auth0.jwt.JWT;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.HttpCode;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.managers.TokenManager;
import uk.brdr.model.User;
import uk.brdr.services.UserService;

public class UserController {

  private final UserService userServiceImpl;
  private final TokenManager tokenManager;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userServiceImpl, TokenManager tokenManager) {
    this.userServiceImpl = userServiceImpl;
    this.tokenManager = tokenManager;
  }

  public void register(Context ctx) {
    try {
      var user = ctx.bodyAsClass(User.class);
      userServiceImpl.save(user);
      ctx.status(HttpCode.CREATED);
    } catch (Exception e) {
      ctx.status(HttpCode.SERVICE_UNAVAILABLE);
    }
  }

  public void login(Context ctx) {
    try {
      var request = ctx.bodyAsClass(User.class);
      var user = userServiceImpl.login(request);
      var token = tokenManager.issueToken(user);
      var cookie = new Cookie("jwt", token, "/", -1, true, 0, true, null, null, null);

      ctx.json(user.getUsername())
          .status(HttpCode.OK)
          .cookie(cookie)
          .header("Content-Type", "application/json");
    } catch (IllegalArgumentException e) {
      ctx.status(HttpCode.UNAUTHORIZED);
    } catch (Exception e) {
      ctx.status(HttpCode.SERVICE_UNAVAILABLE);
    }
  }

  public void validate(Context ctx) {
    var token = Optional.ofNullable(ctx.cookie("jwt"));
    if (token.isPresent()) {
      var verifier = JWT.require(tokenManager.getAlgorithm()).build();
      var jwt = verifier.verify(token.get());
      var userId = Integer.parseInt(jwt.getIssuer());
      var username = userServiceImpl.getUsername(userId);
      ctx.json(username).status(HttpCode.OK);
    } else {
      ctx.status(HttpCode.UNAUTHORIZED);
    }
  }
}
