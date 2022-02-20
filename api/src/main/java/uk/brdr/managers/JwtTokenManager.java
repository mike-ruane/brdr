package uk.brdr.managers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.ForbiddenResponse;
import java.util.Optional;
import javax.swing.text.html.Option;
import uk.brdr.model.User;

public class JwtTokenManager implements TokenManager {

  private final Algorithm algorithm;

  public JwtTokenManager(Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  @Override
  public String issueToken(User user) {
    return JWT.create()
        .withIssuer(String.valueOf(user.getId()))
        .sign(algorithm);
  }

  @Override
  public Optional<DecodedJWT> verifyToken(String token) {
    try {
      var verifier = JWT.require(algorithm).build();
      return Optional.of(verifier.verify(token));
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }
}
