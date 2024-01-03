package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Stats;
import at.technikum.apps.mtcg.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

  List<User> users;

  private final String FIND_ALL_SQL = "SELECT * FROM users";
  private final String SAVE_SQL =
    "INSERT INTO users(id, username, password) VALUES(?, ?, ?)";

  private final Database database = new Database();

  public UserRepository() {
    this.users = new ArrayList<>();
  }

  public Stats getStats(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT wins, losses, elo FROM users WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return new Stats(
          resultSet.getInt("wins"),
          resultSet.getInt("losses"),
          resultSet.getInt("elo")
        );
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void increaseWinsAndElo(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE users SET wins = wins + 1, elo = elo + 3 WHERE username = ?"
        )
    ) {
      statement.setString(1, username);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void increaseLossesAndDecreaseElo(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE users SET losses = losses + 1, elo = elo - 5 WHERE username = ?"
      )
    ) {
      statement.setString(1, username);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
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
        if (resultSet.getString("username").equals("admin")) continue;
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

  public List<User> findAllByElo() {
    this.users = new ArrayList<>();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM users ORDER BY elo DESC, wins DESC"
        );
      ResultSet resultSet = statement.executeQuery();
    ) {
      while (resultSet.next()) {
        if (resultSet.getString("username").equals("admin")) continue;
        Stats stats = new Stats(
          resultSet.getInt("wins"),
          resultSet.getInt("losses"),
          resultSet.getInt("elo")
        );
        User user = new User(
          resultSet.getString("id"),
          resultSet.getString("username"),
          resultSet.getString("password"),
          resultSet.getInt("money"),
          resultSet.getString("name"),
          resultSet.getString("bio"),
          resultSet.getString("image"),
          stats
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
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM users WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        Stats stats = new Stats(
          resultSet.getInt("wins"),
          resultSet.getInt("losses"),
          resultSet.getInt("elo")
        );
        user =
          new User(
            resultSet.getString("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("money"),
            resultSet.getString("name"),
            resultSet.getString("bio"),
            resultSet.getString("image"),
            stats
          );
      }
      if (user != null) return Optional.ofNullable(
        user
      ); else return Optional.empty();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
  }

  public Optional<User> update(String usernameToUpdate, User updatedUser) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE users SET username = ?, password = ?, money = ?, name = ?, bio = ?, image = ? WHERE username = ?"
      );
    ) {
      statement.setString(1, updatedUser.getUsername());
      statement.setString(2, updatedUser.getPassword());
      statement.setInt(3, updatedUser.getMoney());
      statement.setString(4, updatedUser.getName());
      statement.setString(5, updatedUser.getBio());
      statement.setString(6, updatedUser.getImage());
      statement.setString(7, usernameToUpdate);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
    Optional<User> dbUser = find(updatedUser.getUsername());
    return dbUser;
  }

  public User save(User user) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
    ) {
      statement.setString(1, user.getId());
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getPassword());
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
    return user;
  }

  public User delete(User user) {
    // Your implementation here
    // Delete the user and return the deleted user
    return null;
  }

  public int getMoney(String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT money FROM users WHERE username = ?"
      );
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt("money");
      }
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public boolean updateMoney(String username, int money) {
    int moneyBefore = getMoney(username);
    int moneyAfter = moneyBefore - money;
    if (moneyAfter < 0) {
      return false;
    }
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE users SET money = ? WHERE username = ?"
      );
    ) {
      statement.setInt(1, moneyAfter);
      statement.setString(2, username);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}
