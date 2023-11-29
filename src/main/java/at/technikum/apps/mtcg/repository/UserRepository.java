package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Session;
import at.technikum.apps.mtcg.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository {

  List<User> users;

  private final String FIND_ALL_SQL = "SELECT * FROM users";
  private final String SAVE_SQL =
    "INSERT INTO users(id, username, password) VALUES(?, ?, ?)";

  private final Database database = new Database();

  public UserRepository() {
    this.users = new ArrayList<>();
  }

  public List<User> findAll() {
    this.users = new ArrayList<>();

    // recover users from database
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
      ResultSet resultSet = statement.executeQuery();
    ) {
      while (resultSet.next()) {
        User user = new User(
          resultSet.getString("id"),
          resultSet.getString("username"),
          resultSet.getString("password")
        );
        users.add(user);
      }
      return this.users;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Optional<User> find(String username) {
    User user = null;
    System.err.println("find user with username " + username);
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM users WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        user =
          new User(
            resultSet.getString("id"),
            resultSet.getString("username"),
            resultSet.getString("password")
          );
      }
      if (user != null) return Optional.ofNullable(
        user
      ); else return Optional.empty();
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Optional<User> update(
    String usernameToUpdate,
    User updatedUser,
    String token
  ) {
    SessionRepository sessionRepository = new SessionRepository();
    Optional<Session> session = sessionRepository.findByToken(token);
    if (session.isEmpty()) {
      throw new RuntimeException("Session not found");
    }
    Timestamp now = Timestamp.from(Instant.now());
    if (session.get().getExpires().before(now)) {
      throw new RuntimeException("Session expired");
    }
    Optional<User> userToUpdate = find(usernameToUpdate);
    if (userToUpdate.isEmpty()) {
      throw new RuntimeException("Something went wrong");
    }
    if (!userToUpdate.get().getUsername().equals(session.get().getUsername())) {
      throw new RuntimeException("Not allowed to do this");
    }
    try {
      sessionRepository.delete(session.get().getUsername());
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE users SET username = ?, password = ? WHERE username = ?"
      );
    ) {
      statement.setString(1, updatedUser.getUsername());
      statement.setString(2, updatedUser.getPassword());
      statement.setString(3, usernameToUpdate);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
    User dbUser = find(updatedUser.getUsername()).get();
    sessionRepository.save(dbUser, Optional.ofNullable(token));
    return Optional.of(dbUser);
  }

  public Optional<User> save(User user) {
    // use UUID to generate a unique id
    user.setId(UUID.randomUUID().toString());
    // save user to database
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
    ) {
      statement.setString(1, user.getId());
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getPassword());
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
    return Optional.of(user);
  }

  public User delete(User user) {
    // Your implementation here
    // Delete the user and return the deleted user
    return null;
  }
}
