package uk.brdr.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.HttpCode;
import io.javalin.http.SameSite;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.model.User;
import uk.brdr.services.UserService;

public class UserController {

  private final UserService userServiceImpl;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private static final Algorithm algorithmHS = Algorithm.HMAC256("secret");

  public UserController(UserService userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
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
      var token = userServiceImpl.login(request);
      var cookie = new Cookie("jwt", token, "/", -1, true, 0, true, null, null, null);

      ctx.status(HttpCode.OK).cookie(cookie).header("Content-Type", "application/json");
    } catch (Exception e) {
      ctx.status(HttpCode.SERVICE_UNAVAILABLE);
    }
  }

  public void validate(Context ctx) {
    var token = Optional.ofNullable(ctx.cookie("jwt"));
    if (token.isEmpty()) {
      ctx.status(HttpCode.UNAUTHORIZED);
    }

    var verifier = JWT.require(algorithmHS).build();
    var jwt = verifier.verify(token.get());
    var userId = Integer.parseInt(jwt.getIssuer());
    ctx.json(userId).status(HttpCode.OK);
  }
}
