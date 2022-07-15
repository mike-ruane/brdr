package uk.brdr.managers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Optional;
import uk.brdr.model.User;
import uk.brdr.properties.JwtProperties;

public class JwtTokenManager implements TokenManager {

  private final Algorithm algorithm;

  public JwtTokenManager(JwtProperties jwtProperties) {
    this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret());;
  }

  @Override
  public Algorithm getAlgorithm() {
    return algorithm;
  }

  @Override
  public String issueToken(User user) {
    return JWT.create().withIssuer(String.valueOf(user.getId())).sign(algorithm);
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
