package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class historyRepository {

  private static final Database database = new Database();

  public void saveEvent(String id, String type, String[] users, String result) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO history(id, type, users, result) VALUES(?, ?, ?, ?)"
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
