package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.EventHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class historyRepository {

  private static final Database database = new Database();

  public EventHistory[] getHistory(String token) {
    List<EventHistory> historyList = new ArrayList<>();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM history"
      );
    ) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        EventHistory eventHistory = new EventHistory(
          resultSet.getString("id"),
          resultSet.getString("type"),
          resultSet.getString("users").split(","),
          resultSet.getTimestamp("time").toString(),
          resultSet.getString("result")
        );
        historyList.add(eventHistory);
      }
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
    return historyList.toArray(new EventHistory[0]);
  }

  public void saveEvent(String id, String type, String[] users, String result) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO history(id, type, users, result, time) VALUES(?, ?, ?, ?, ?)"
      );
    ) {
      String usersString = "";
      for (int i = 0; i < users.length; i++) {
        usersString += users[i];
        if (i != users.length - 1) {
          usersString += ",";
        }
      }
      statement.setString(1, id);
      statement.setString(2, type);
      statement.setString(3, usersString);
      statement.setString(4, result);
      statement.setTimestamp(5, Timestamp.from(Instant.now()));
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }

  public boolean isEventSaved(String id) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM history WHERE id = ?"
      );
    ) {
      statement.setString(1, id);
      return statement.executeQuery().next();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong", e);
    }
  }
}
