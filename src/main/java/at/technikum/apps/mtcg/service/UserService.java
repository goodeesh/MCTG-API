package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

  private final UserRepository UserRepository;
  private final Auth auth;

  public UserService(UserRepository UserRepository, Auth auth) {
    this.UserRepository = UserRepository;
    this.auth = auth;
  }

  public UserService(UserRepository UserRepository) {
    this.UserRepository = UserRepository;
    this.auth = new Auth();
  }

  public UserService() {
    this.UserRepository = new UserRepository();
    this.auth = new Auth();
  }

  public List<User> findAll() {
    return UserRepository.findAll();
  }

  public User save(User user) {
    user.setId(UUID.randomUUID().toString());
    if (
      !UserRepository.find(user.getUsername()).isPresent()
    ) return UserRepository.save(user); else throw new RuntimeException(
      "Username already exists"
    );
  }

  public User find(String username, String token) {
    if (auth.hasAccess(username, token).equals(true)) {
      Optional<User> user = UserRepository.find(username);
      if (user.isPresent()) return user.get(); else throw new RuntimeException(
        "User not found"
      );
    }
    throw new RuntimeException("Not allowed to do this");
  }

  public Optional<User> update(
    String usernameToUpdate,
    User updatedUser,
    String token
  ) {
    if (auth.hasAccess(usernameToUpdate, token).equals(true)) {
      Optional<User> user = UserRepository.find(usernameToUpdate);
      if (user.isPresent()) {
        if (updatedUser.getId() == null) updatedUser.setId(user.get().getId());
        if (updatedUser.getUsername() == null) updatedUser.setUsername(
          user.get().getUsername()
        );
        if (updatedUser.getPassword() == null) updatedUser.setPassword(
          user.get().getPassword()
        );

        if (updatedUser.getMoney() == null) updatedUser.setMoney(
          user.get().getMoney()
        );
        if (updatedUser.getName() == null) updatedUser.setName(
          user.get().getName()
        );

        if (updatedUser.getBio() == null) updatedUser.setBio(
          user.get().getBio()
        );
        if (updatedUser.getImage() == null) updatedUser.setImage(
          user.get().getImage()
        );
        return UserRepository.update(usernameToUpdate, updatedUser);
      } else throw new RuntimeException("User not found");
    }
    throw new RuntimeException("Not allowed to do this");
  }
}
