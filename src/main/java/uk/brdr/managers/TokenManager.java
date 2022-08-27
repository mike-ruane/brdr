package uk.brdr.managers;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import uk.brdr.model.User;

public interface TokenManager {

  String issueToken(User user);

  DecodedJWT verifyToken(String token);

  Algorithm getAlgorithm();
}
