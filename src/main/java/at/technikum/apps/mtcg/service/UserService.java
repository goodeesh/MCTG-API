package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

  private final UserRepository UserRepository;

  public UserService(UserRepository UserRepository) {
    this.UserRepository = UserRepository;
  }

  public UserService() {
    this.UserRepository = new UserRepository();
  }

  public List<User> findAll() {
    return UserRepository.findAll();
  }

  public Optional<User> find(int id) {
    return Optional.empty();
  }

  public User save(User user) {
    user.setId(UUID.randomUUID().toString());
    if (
      UserRepository.find(user.getUsername()).isPresent()
    ) return UserRepository.save(user); else throw new RuntimeException(
      "Username already exists"
    );
  }

  public User find(String username, String token) {
    Auth auth = new Auth();
    if (auth.hasAccess(username, token).equals(true)) {
      return UserRepository.find(username).get();
    }
    throw new RuntimeException("Not allowed to do this");
  }

  public Optional<User> update(
    String usernameToUpdate,
    User updatedUser,
    String token
  ) {
    return UserRepository.update(usernameToUpdate, updatedUser, token);
  }

  public User delete(User User) {
    return null;
  }

  public User test(User user) {
    user.setId("testId");
    return user;
  }
}
