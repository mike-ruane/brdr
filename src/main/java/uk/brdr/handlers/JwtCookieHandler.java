package uk.brdr.handlers;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.UnauthorizedResponse;
import java.util.Optional;
import uk.brdr.managers.TokenManager;

public class JwtCookieHandler {

  public static Optional<String> getTokenFromCookie(Context context) {
    return Optional.ofNullable(context.cookie("jwt"));
  }

  public static void addDecodedToContext(Context context, DecodedJWT jwt) {
    context.attribute("jwt", jwt);
  }

  public static DecodedJWT getDecodedFromContext(Context context) {
    Object attribute = context.attribute("jwt");

    if (!(attribute instanceof DecodedJWT)) {
      throw new InternalServerErrorResponse("The context carried invalid object as JavalinJWT");
    }

    return (DecodedJWT) attribute;
  }

  public static Handler createCookieDecodeHandler(TokenManager tokenManager) {
    return context -> {
      var isUserView = Optional.ofNullable(context.queryParam("username")).isPresent();
      if (isUserView) {
        return;
      }
      var token = getTokenFromCookie(context).orElseThrow(UnauthorizedResponse::new);
      var decodedJWT = tokenManager.verifyToken(token).orElseThrow(UnauthorizedResponse::new);
      addDecodedToContext(context, decodedJWT);
    };
  }
}
