package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

  public boolean isLobbyEmpty() {
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
}
