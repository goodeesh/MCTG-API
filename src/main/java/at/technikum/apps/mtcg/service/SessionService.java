package at.technikum.apps.mtcg.service;

import java.util.List;
import java.util.Optional;

import at.technikum.apps.mtcg.entity.Session;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.SessionRepository;



public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService() {
        this.sessionRepository = new SessionRepository();
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Optional<Session> find(int id) {
        return Optional.empty();
    }

    public Optional<Session> save(User user) {
        return sessionRepository.save(user, null);
    }

    public Optional<Session> find(String username) {
        return sessionRepository.find(username);
    }

    public Session delete(Session user) {
        return null;
    }
}
