package uk.brdr.data.dao;

import java.util.Optional;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.mappers.UserRowMapper;
import uk.brdr.model.User;

public class UserDaoImpl implements UserDao {

  private final Jdbi jdbi;

  private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
  private static final UserRowMapper userRowMapper = new UserRowMapper();

  public UserDaoImpl(DataSource dataSource) {
    this.jdbi = Jdbi.create(dataSource);
  }

  @Override
  public void addUser(User user) {
    try {
      jdbi.useHandle(
              handle ->
                  handle
                      .createUpdate(
                          "INSERT INTO users(password, email, username) VALUES(:password, :email, :username)")
                      .bind("password", user.getPassword())
                      .bind("email", user.getEmail())
                      .bind("username", user.getUsername())
                  .execute());
    } catch (Exception e) {
      logger.error("failed to create user: {}", e.getMessage());
      throw e;
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    try {
      return jdbi.withHandle(
          handle ->
              handle
                  .createQuery("SELECT * FROM users WHERE email = :email")
                  .bind("email", email)
                  .map(userRowMapper)
                  .findFirst());
    } catch (Exception e) {
      logger.error("failed to execute findByEmail query: {}", e.getMessage());
      throw e;
    }
  }
}
