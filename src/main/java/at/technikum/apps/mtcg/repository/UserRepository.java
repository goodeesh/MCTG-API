package at.technikum.apps.mtcg.repository;

import java.util.List;
import java.util.Optional;

import at.technikum.apps.mtcg.entity.User;

public class UserRepository {
    public List<User> findAll() {
        // Your implementation here
        // Return a list of all users
        return null;
    }

    public Optional<User> find(int id) {
        // Your implementation here
        // Return an optional user with the specified id
        return null;
    }

    public User save(User user) {
        // Your implementation here
        // Save the user and return the saved user
        return user;
    }

    public User delete(User user) {
        // Your implementation here
        // Delete the user and return the deleted user
        return null;
    }
}