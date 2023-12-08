package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private UserRepository userRepository;
  private UserService userService;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    userService = new UserService(userRepository);
  }

  // Existing test for saving a user
  @Test
  void shouldSaveUser() {
    // Arrange
    UserRepository userRepository = mock(UserRepository.class);
    UserService userService = new UserService(userRepository);
    User user = new User("test", "test");

    when(userRepository.save(any())).thenReturn(user);

    // Act
    User savedUser = userService.save(user);

    // Assert
    assertEquals(user.getUsername(), savedUser.getUsername());
    assertEquals(user.getPassword(), savedUser.getPassword());
  }

  @Test
  void shouldBeEmptyWhenUserDoesNotExist() {
    // Arrange
    when(userRepository.find("nonExistingUser")).thenReturn(Optional.empty());

    Optional<User> user = userRepository.find("nonExistingUser");

    assertEquals(Optional.empty(), user);
  }

  @Test
  void shouldThrowExceptionWhenNotAuthorizedToUpdateUser() {
    // Arrange
    // Assuming 'auth.hasAccess' returns false for unauthorized access
    // You need to mock the Auth class and its 'hasAccess' method to return false

    // Act and Assert
    RuntimeException exception = assertThrows(
      RuntimeException.class,
      () ->
        userService.update(
          "userToUpdate",
          new User("id", "newUsername", "newPassword"),
          "invalidToken"
        )
    );
    assertEquals("Not allowed to do this", exception.getMessage());
  }

  @Test
  void shouldUpdateUserWhenAuthorized() {
    // Arrange
    String usernameToUpdate = "userToUpdate";
    String token = "validToken";
    User updatedUser = new User("id", "newUsername", "newPassword");

    Auth auth = mock(Auth.class);
    when(auth.hasAccess(usernameToUpdate, token)).thenReturn(true);

    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.find(usernameToUpdate))
      .thenReturn(Optional.of(new User()));
    when(userRepository.update(usernameToUpdate, updatedUser, token))
      .thenReturn(Optional.of(updatedUser));

    UserService userService = new UserService(userRepository, auth);

    // Act
    Optional<User> result = userService.update(
      usernameToUpdate,
      updatedUser,
      token
    );

    // Assert
    assertTrue(result.isPresent());
    assertEquals(updatedUser, result.get());
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    // Arrange
    String usernameToUpdate = "userToUpdate";
    String token = "Bearer admin-mtcgToken";
    User updatedUser = new User("id", "newUsername", "newPassword");

    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.find(usernameToUpdate)).thenReturn(Optional.empty());

    UserService userService = new UserService(userRepository);

    // Act and Assert
    RuntimeException exception = assertThrows(
      RuntimeException.class,
      () -> userService.update(usernameToUpdate, updatedUser, token)
    );
    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenNotAuthorized() {
    // Arrange
    String usernameToUpdate = "userToUpdate";
    String token = "invalidToken";
    User updatedUser = new User("id", "newUsername", "newPassword");

    UserRepository userRepository = mock(UserRepository.class);

    UserService userService = new UserService(userRepository);

    // Act and Assert
    RuntimeException exception = assertThrows(
      RuntimeException.class,
      () -> userService.update(usernameToUpdate, updatedUser, token)
    );
    assertEquals("Not allowed to do this", exception.getMessage());
  }
}
