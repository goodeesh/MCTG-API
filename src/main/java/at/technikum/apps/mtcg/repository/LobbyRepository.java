package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

public class LobbyRepository {

  private final Database database = new Database();

  public boolean findBattle(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT COUNT(*) FROM lobby"
      );
    ) {
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        int count = resultSet.getInt(1);
        return count == 0;
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    return false;
  }

  public boolean isLobbyEmpty(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT COUNT(*) FROM lobby"
      );
    ) {
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        int count = resultSet.getInt(1);
        if (!isUsernameInLobby(username)) {
          enterLobby(username);
          return count == 0;
        } else {
          System.err.println("he is in lobby");
          return (count == 1);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    return false;
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

  public boolean enterLobby(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO lobby(username, date) VALUES(?, ?)"
      );
    ) {
      System.err.println("trying to enter lobby");
      statement.setString(1, username);
      statement.setTimestamp(2, Timestamp.from(Instant.now()));
      ResultSet resultSet = statement.executeQuery();
      System.err.println("execute enter user in lobby");
      if (resultSet.next()) {
        return true;
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    System.err.println("I guess it didnt work");
    return false;
  }
}
