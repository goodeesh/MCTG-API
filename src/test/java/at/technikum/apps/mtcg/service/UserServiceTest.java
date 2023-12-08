package at.technikum.apps.mtcg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Test
  void shouldSaveUser() {
    // Arrange
    UserRepository userRepository = mock(UserRepository.class);
    UserService userService = new UserService(userRepository);
    User user = new User("test", "test", "test");

    when(userRepository.save(any())).thenReturn(user);

    // Act
    User savedUser = userService.save(user);

    // Assert
    assertEquals(user.getId(), savedUser.getId());
    assertEquals(user.getUsername(), savedUser.getUsername());
    assertEquals(user.getPassword(), savedUser.getPassword());
  }
}
