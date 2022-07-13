package uk.brdr.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import uk.brdr.model.User;

public class HashingUtils {

  public static final Argon2 ARGON_2 = Argon2Factory.create();

  public User hashUserPassword(User user) {
    try {
      var hashedPassword = ARGON_2.hash(5, 65536, 1, user.getPassword().toCharArray());
      return new User(user.getId(), user.getUsername(), user.getEmail(), hashedPassword);
    } finally {
      ARGON_2.wipeArray(user.getPassword().toCharArray());
    }
  }

  public boolean validateUser(User dbUser, User user) {
    return ARGON_2.verify(dbUser.getPassword(), user.getPassword().toCharArray());
  }

}
