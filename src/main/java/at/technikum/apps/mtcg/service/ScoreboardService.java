package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;

public class ScoreboardService {

  private UserRepository userRepository = new UserRepository();

  public List<User> displayScoreboard(String token) {
    if (token == null || token.isEmpty() || token.equals("")) {
      throw new RuntimeException("Not allowed to do this");
    }
    try {
      return userRepository.findAllByElo();
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
  }
}
