package uk.brdr.data;

import java.util.Optional;
import uk.brdr.model.User;

public interface UserRepository {
  void create(User user);
  Optional<User> findByEmail(String email);

}
