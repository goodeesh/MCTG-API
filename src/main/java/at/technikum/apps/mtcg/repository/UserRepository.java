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
    private final String SAVE_SQL = "INSERT INTO users(id, name, address) VALUES(?, ?, ?)";

    private final Database database = new Database();

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public List<User> findAll() {
        this.users = new ArrayList<>();

        //recover users from database
        try (
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();
        ){
            while (resultSet.next()) {
                User user = new User(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("address")
                );
                users.add(user);
            }
            return this.users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<User> find(int id) {
        // Your implementation here
        // Return an optional user with the specified id
        return null;
    }

    public Optional<User> update(String updateId, User updatedUser) {
        int indexToUpdate = -1; // -1 means not found
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updateId)) {
                indexToUpdate = i;
                break;
            }
        }
        if (indexToUpdate != -1) {
            users.set(indexToUpdate, updatedUser);
        } else {
            return Optional.empty();
        }
        return Optional.of(updatedUser);
    }

    public User save(User user) {
        //use UUID to generate a unique id
        user.setId(UUID.randomUUID().toString());
        //save user to database
        try (
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
        ){
            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getAddress());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public User delete(User user) {
        // Your implementation here
        // Delete the user and return the deleted user
        return null;
    }
}