package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.LobbyRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.lang.Thread;

public class BattleService {

  Auth auth = new Auth();
  private UserRepository userRepository = new UserRepository();
  private CardRepository cardRepository = new CardRepository();
  private LobbyRepository lobbyRepository = new LobbyRepository();

  public String startBattle(String token) {
    if (token == null || token.isEmpty() || token.equals("")) {
      throw new RuntimeException("Not allowed to do this");
    }
    User user = userRepository.find(auth.extractUsernameFromToken(token)).get();
    if (
      cardRepository
        .getCardsFromUserWhereInDeckTrue(user.getUsername())
        .size() <
      4
    ) {
      throw new RuntimeException("Not enough cards in deck");
    }
    try {
      while (lobbyRepository.isLobbyEmpty(user.getUsername())) {
        Thread.sleep(1000);
      }
      User userToFight = lobbyRepository.findBattle(user.getUsername());
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
    return "Battle started";
  }
}
