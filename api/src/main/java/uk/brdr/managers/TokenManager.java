package uk.brdr.managers;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Optional;
import uk.brdr.model.User;

public interface TokenManager {

  String issueToken(User user);

  Optional<DecodedJWT> verifyToken(String token);

}
