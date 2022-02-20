package uk.brdr.data.dao;

import java.util.Optional;
import uk.brdr.model.User;

public interface UserDao {

  void addUser(User user);

  Optional<User> findByEmail(String email);
}
