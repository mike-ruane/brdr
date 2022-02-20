package uk.brdr.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import java.util.Optional;
import uk.brdr.model.User;
import uk.brdr.services.UserServiceImpl;

public class UserController {

  private final UserServiceImpl userServiceImpl;
  private static final Algorithm algorithmHS = Algorithm.HMAC256("secret");

  public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  public void register(Context ctx) {
    try {
      userServiceImpl.save(ctx.bodyAsClass(User.class));
      ctx.status(HttpCode.CREATED);
    } catch (Exception e) {
      ctx.status(HttpCode.SERVICE_UNAVAILABLE);
    }
  }

  public void login(Context ctx) {
    try {
      var request = ctx.bodyAsClass(User.class);
      var token = userServiceImpl.login(request);
      ctx.status(HttpCode.OK).cookie("jwt", token);
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
