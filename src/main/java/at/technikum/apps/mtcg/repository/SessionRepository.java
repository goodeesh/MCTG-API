package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Session;
import at.technikum.apps.mtcg.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionRepository {

  List<Session> sessions;

  private final String FIND_ALL_SQL = "SELECT * FROM sessions";
  private final String SAVE_SQL =
    "INSERT INTO sessions(id, token, username,created,expires) VALUES(?,?,?,?,?)";
  private final String DELETE_SQL = "DELETE FROM sessions WHERE username = ?";

  private final Database database = new Database();

  public SessionRepository() {
    this.sessions = new ArrayList<>();
  }

  public List<Session> findAll() {
    this.sessions = new ArrayList<>();

    // recover sessions from database
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
      ResultSet resultSet = statement.executeQuery();
    ) {
      while (resultSet.next()) {
        Session session = new Session(resultSet.getString("userId"));
        sessions.add(session);
      }
      return this.sessions;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Optional<Session> find(String username) {
    Session session = null;
    System.err.println("find session with id " + username);
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM sessions WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        session = new Session(resultSet.getString("username"));
      }
      if (session != null) return Optional.ofNullable(
        session
      ); else return Optional.empty();
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Optional<Session> findByToken(String token) {
    Session session = null;
    System.err.println("find session with token " + token);
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM sessions WHERE token = ?"
      );
    ) {
      statement.setString(1, token);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        session = new Session(resultSet.getString("username"));
      }
      if (session != null) return Optional.ofNullable(
        session
      ); else return Optional.empty();
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Optional<Session> save(User user, Optional<String> token) {
    // save session to database
    UserRepository userRepository = new UserRepository();
    Optional<User> userOptional = null;
    try {
      userOptional = userRepository.find(user.getUsername());
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }

    if (!userOptional.get().getPassword().equals(user.getPassword())) {
      throw new RuntimeException("Invalid username/password provided");
    }
    if (find(user.getUsername()).isPresent()) {
      delete(user.getUsername());
    }
    Session session = new Session(userOptional.get().getUsername());
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
    ) {
      statement.setString(1, session.getId());
      statement.setString(2, session.getToken());
      statement.setString(3, session.getUsername());
      statement.setTimestamp(4, session.getCreated());
      statement.setTimestamp(5, session.getExpires());
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Internal server error");
    }
    return Optional.of(session);
  }

  public boolean delete(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
    ) {
      statement.setString(1, username);
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
