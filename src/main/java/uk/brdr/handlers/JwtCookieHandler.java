package uk.brdr.handlers;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import java.util.Optional;
import uk.brdr.managers.TokenManager;

public class JwtCookieHandler {

  private final TokenManager tokenManager;

  public JwtCookieHandler(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }

  public DecodedJWT verifyJwtTokenFromContext(Context ctx) {
    var token = Optional.ofNullable(ctx.cookie("jwt")).orElseThrow(UnauthorizedResponse::new);
    return tokenManager.verifyToken(token);
  }

  public Integer getUserIdFromJWT(DecodedJWT jwt) {
    return Integer.parseInt(jwt.getIssuer());
  }
}
