package at.technikum.apps.mtcg.service;

import java.util.List;
import java.util.Optional;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;

public class UserService {
    private final UserRepository UserRepository;

    public UserService() {
        this.UserRepository = new UserRepository();
    }

    public List<User> findAll() {
        return UserRepository.findAll();
    }

    public Optional<User> find(int id) {
        return Optional.empty();
    }

    public Optional<User> save(User User) {
        return UserRepository.save(User);
    }

    public Optional<User> find(String id) {
        return UserRepository.find(id);
    }

    public Optional<User> update(String updateId, User updatedUser, String username) {
        return UserRepository.update(updateId, updatedUser, username);
    }

    public User delete(User User) {
        return null;
    }
}
