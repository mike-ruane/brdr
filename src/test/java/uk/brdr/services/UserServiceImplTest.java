package uk.brdr.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.javalin.http.BadRequestResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.brdr.data.dao.UserDao;
import uk.brdr.data.dao.UserDaoImpl;
import uk.brdr.model.User;

public class UserServiceImplTest {

  UserDao userDao = mock(UserDaoImpl.class);
  UserService userService;
  User user = new User(1, "mikeyru", "mike@ruane.com", "secure-password");

  @BeforeEach
  void setup() {
    userService = new UserServiceImpl(userDao);
  }

  @Test
  void saveNewUser() {
    when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());
    userService.save(user);
    verify(userDao).addUser(user);
  }

  @Test
  void dontSaveUserIfExists() {
    when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    assertThrows(BadRequestResponse.class, () -> userService.save(user));
    verify(userDao, never()).addUser(user);
  }

  @Test
  void successfulLogin() {
    when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    var actual = userService.login(user);
    assertEquals(actual, user);
  }

  @Test
  void userDoesntExist() {
    when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());
    assertThrows(BadRequestResponse.class, () -> userService.login(user));
  }

  @Test
  void userIncorrectPassword() {
    var userDbEntry =
        new User(user.getId(), user.getUsername(), user.getEmail(), "some-other-password");
    when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(userDbEntry));
    assertThrows(BadRequestResponse.class, () -> userService.login(user));
  }
}
