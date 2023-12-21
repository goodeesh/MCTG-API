package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;

public class ScoreboardService {

  private UserRepository userRepository = new UserRepository();

  public List<User> displayScoreboard() {
    List<User> users = userRepository.findAllByElo();
    return users;
  }
}
