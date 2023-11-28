package at.technikum.apps.mtcg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.User;
import java.util.UUID;

public class UserRepository {
    List<User> users;

    private final String FIND_ALL_SQL = "SELECT * FROM users";
    private final String SAVE_SQL = "INSERT INTO users(id, username, password) VALUES(?, ?, ?)";

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
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
                users.add(user);
            }
            return this.users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<User> find(String id) {
        User user = null;
        // recover user from database
        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"));
            }
            if (user != null)
                return Optional.ofNullable(user);
            else
                return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> update(String updateId, User updatedUser, String username) {
        
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        }
        Optional<User> userToUpdate = find(updateId);
        if (userToUpdate.isEmpty()) {
            return Optional.empty();
        }
        if (!userToUpdate.get().getUsername().equals(username)) {
            return Optional.empty();
        }
        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE users SET username = ?, password = ? WHERE id = ?");) {
            statement.setString(1, updatedUser.getUsername());
            statement.setString(2, updatedUser.getPassword());
            statement.setString(3, updateId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        updatedUser.setId(updateId);
        return Optional.of(updatedUser);
    }

    public Optional<User> save(User user) {
        // use UUID to generate a unique id
        user.setId(UUID.randomUUID().toString());
        // save user to database
        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(SAVE_SQL);) {
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