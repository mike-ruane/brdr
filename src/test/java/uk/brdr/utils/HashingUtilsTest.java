package uk.brdr.utils;

import static org.junit.jupiter.api.Assertions.*;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.jupiter.api.Test;
import uk.brdr.model.User;

class HashingUtilsTest {

  private static final Argon2 ARGON_2 = Argon2Factory.create();
  HashingUtils hashingUtils = new HashingUtils();

  @Test
  void hashUserPassword() {
    var user = new User(1, "test-user", "test-user@test.com", "test-password");
    var hashedUser = hashingUtils.hashUserPassword(user);
    var expectedPassword = hashedUser.getPassword();
    assertTrue(ARGON_2.verify(expectedPassword, user.getPassword().toCharArray()));
  }

  @Test
  void validateUser() {
    var hashedPassword = ARGON_2.hash(5, 65536, 1, "test-password".toCharArray());
    var dbUser = new User(1, "test-user", "test-user@test.com", hashedPassword);
    var user = new User(1, "test-user", "test-user@test.com", "test-password");
    assertTrue(hashingUtils.validateUser(dbUser, user));
  }
}
