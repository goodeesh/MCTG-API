package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserService {

  private final UserRepository UserRepository;

  public UserService() {
    this.UserRepository = new UserRepository();
  }

  public List<User> findAll() {
    return UserRepository.findAll();
  }

  public Optional<User> find(int id) {
    return Optional.empty();
  }

  public Optional<User> save(User User) {
    return UserRepository.save(User);
  }

  public Optional<User> find(String username) {
    return UserRepository.find(username);
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
}
