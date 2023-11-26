package at.technikum.apps.mtcg.service;

import java.util.ArrayList;
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

    public User save(User User) {
        return UserRepository.save(User);
    }

    public Optional<User> update(int updateId, User updatedUser) {
        return UserRepository.update(updateId, updatedUser);
    }

    public User delete(User User) {
        return null;
    }
}
