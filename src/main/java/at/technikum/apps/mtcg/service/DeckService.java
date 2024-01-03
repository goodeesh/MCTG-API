package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;

public class DeckService {

  private final CardRepository cardRepository;
  private final UserRepository userRepository;

  public DeckService() {
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
    return cardRepository.getCardsFromUserWhereInDeckTrue(username);
  }

  public List<Card> changueDeck(
  String[] cardIds,
  String username,
  String token
) {
  Auth auth = new Auth();
  if (!auth.hasAccess(username, token)) {
    throw new RuntimeException("Not allowed to do this");
  } else if (userRepository.find(username).isEmpty()) {
    throw new RuntimeException("User not found");
  } else if (cardIds.length != 4) {
    throw new RuntimeException("Bad request");
  }
  for (String cardId : cardIds) {
    if (!cardRepository.doesCardBelongToUser(cardId, username)) {
      throw new RuntimeException("Not allowed to do this");
    }
  }
  boolean updateDeck = cardRepository.setCardsInDeckToTrue(username, cardIds);
  if (updateDeck) {
    return cardRepository.getCardsFromUserWhereInDeckTrue(username);
  } else {
    throw new RuntimeException("Not allowed to do this");
  }
}
}
