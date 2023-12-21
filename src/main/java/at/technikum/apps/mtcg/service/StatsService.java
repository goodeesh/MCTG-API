package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Stats;
import at.technikum.apps.mtcg.repository.UserRepository;

public class StatsService {

  private UserRepository userRepository;

  public StatsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public StatsService() {
    this.userRepository = new UserRepository();
  }

  public Stats getStatsFromUser(String token) {
    Auth auth = new Auth();
    String username = auth.extractUsernameFromToken(token);
    Stats stats = userRepository.getStats(username);
    return stats;
  }
}
