package uk.brdr.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.HttpCode;
import java.util.Optional;
import uk.brdr.model.User;
import uk.brdr.services.UserService;

public class UserController {

  private final UserService userService;
  private static final Algorithm algorithmHS = Algorithm.HMAC256("secret");

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public void register(Context ctx) {
    try {
      userService.save(ctx.bodyAsClass(User.class));
      ctx.status(HttpCode.CREATED);
    } catch (Exception e) {
      ctx.status(500);
    }
  }

  public void login(Context ctx) {
    try {
      var request = ctx.bodyAsClass(User.class);
      var user = userService.findByEmail(request.getEmail());
      if (user.isEmpty()) {
        ctx.status(HttpCode.BAD_REQUEST);
      }
      if (!request.comparePassword(user.get().getPassword())) {
        ctx.status(HttpCode.BAD_REQUEST);
      }

      var token = JWT.create()
          .withIssuer(String.valueOf(user.get().getId()))
          .sign(algorithmHS);

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
    var verifier = JWT.require(algorithmHS)
        .withIssuer()
        .build(); //Reusable verifier instance
    var jwt = verifier.verify(token.get());
    var userId = Integer.parseInt(jwt.getIssuer());
    ctx.json(userId);
  }

}
