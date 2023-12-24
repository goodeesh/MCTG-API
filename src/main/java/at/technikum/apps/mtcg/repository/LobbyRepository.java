package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LobbyRepository {

  private final UserRepository userRepository = new UserRepository();

  private final Database database = new Database();

  public User findBattle(String username) {
    List<User> users = new ArrayList<>();
    List<String> usernameString = new ArrayList<>();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM lobby"
      );
    ) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        System.err.println(
          "first user has username " + resultSet.getString("username")
        );
        if (resultSet.getString("username").equals(username)) {
          System.err.println("skipping user");
        } else {
          System.err.println("adding username to list");
          usernameString.add(resultSet.getString("username"));
          System.err.println("added username to list");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    for (String user : usernameString) {
      if (userRepository.find(user).isEmpty()) continue;
      users.add(userRepository.find(user).get());
    }
    User initialUser = userRepository.find(username).get();
    int elo = initialUser.getStats().getElo();
    int eloDiff = 100000;
    User closestUser = null;
    for (User u : users) {
      if (Math.abs(u.getStats().getElo() - elo) < eloDiff) {
        eloDiff = Math.abs(u.getStats().getElo() - elo);
        closestUser = u;
      }
    }
    System.err.println("closest user: " + closestUser.getUsername());
    return closestUser;
  }

  public boolean isLobbyEmpty(String username) {
    int count = 0;
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT COUNT(*) FROM lobby"
      );
    ) {
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
      resultSet.close();
      statement.close();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong in isLobbyEmpty", e);
    }

    if (!isUsernameInLobby(username)) {
      enterLobby(username);
      System.err.println("he is not in lobby");
      return count == 0;
    } else {
      System.err.println("he is in lobby");
      return count == 1;
    }
  }

  public boolean isUsernameInLobby(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM lobby WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        if (resultSet.getString("username").equals(username)) return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    return false;
  }

  public void enterLobby(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO lobby(username, date) VALUES(?, ?)"
      );
    ) {
      statement.setString(1, username);
      statement.setTimestamp(2, Timestamp.from(Instant.now()));
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }

  public void userIsReady(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE lobby SET ready = ? WHERE username = ?"
      );
    ) {
      statement.setBoolean(1, true);
      statement.setString(2, username);
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }

  public boolean isUserReady(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT ready FROM lobby WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getBoolean("ready");
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    return false;
  }

  public void userIsNotReady(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE lobby SET ready = ? WHERE username = ?"
      );
    ) {
      statement.setBoolean(1, false);
      statement.setString(2, username);
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }

  public void leaveLobby(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "DELETE FROM lobby WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      System.err.println("deleting user from lobby with name: " + username);
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }

  public void selectCard(String username, String cardName) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE lobby SET card = ? WHERE username = ?"
      );
    ) {
      statement.setString(1, cardName);
      statement.setString(2, username);
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }

  public String getCardToFight(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT card FROM lobby WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getString("card");
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    return null;
  }
}
