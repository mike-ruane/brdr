package uk.brdr.data.daoimpl;

import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.dao.UserDao;
import uk.brdr.data.mappers.UserRowMapper;
import uk.brdr.model.User;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

  private final Jdbi jdbi;

  private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
  private static final UserRowMapper userRowMapper = new UserRowMapper();
  public UserDaoImpl(DataSource dataSource) { this.jdbi = Jdbi.create(dataSource); }

  @Override
  public void addUser(User user) {
    try {
      jdbi.withHandle(handle ->
          handle.createUpdate("INSERT INTO users(username, password) VALUES(:username, :password)")
              .bind("username", user.getEmail())
              .bind("password", user.getPassword())
      ).execute();
    } catch (Exception e) {
      logger.error("failed to create user: {}", e.getMessage());
      throw e;
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    try {
      return jdbi.withHandle(handle ->
              handle.createQuery("SELECT * FROM users WHERE email = :email")
                  .bind("email", email)
                  .map(userRowMapper)
                  .findFirst()
          );
    } catch (Exception e) {
      logger.error("failed to execute findByEmail query: {}", e.getMessage());
      throw e;
    }
  }
}
