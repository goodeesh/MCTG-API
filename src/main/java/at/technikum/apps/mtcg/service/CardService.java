package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;

public class CardService {

  private final CardRepository cardRepository;
  private final UserRepository userRepository;

  public CardService() {
    this.cardRepository = new CardRepository();
    this.userRepository = new UserRepository();
  }

  public List<Card> getCardsFromUser(String username, String token) {
    Auth auth = new Auth();
    if (!auth.hasAccess(username, token)) {
      throw new RuntimeException("Not allowed to do this");
    } else if (userRepository.find(username).isEmpty()) {
      throw new RuntimeException("User not found");
    }
    return cardRepository.getCardsFromUser(username);
  }
}
