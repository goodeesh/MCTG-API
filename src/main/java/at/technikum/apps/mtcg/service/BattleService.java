package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.helper.Helper;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.LobbyRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.lang.Thread;
import java.util.List;

public class BattleService {

  Auth auth = new Auth();
  private UserRepository userRepository = new UserRepository();
  private CardRepository cardRepository = new CardRepository();
  private LobbyRepository lobbyRepository = new LobbyRepository();

  public String startBattle(String token) {
    String username = auth.extractUsernameFromToken(token);
    if (token == null || token.isEmpty() || token.equals("")) {
      throw new RuntimeException("Not allowed to do this");
    }
    User user = userRepository.find(auth.extractUsernameFromToken(token)).get();
    List<Card> cards = cardRepository.getCardsFromUser(user.getUsername());
    if (cards.size() < 4) {
      throw new RuntimeException("Not enough cards in deck");
    }
    try {
      while (lobbyRepository.isLobbyEmpty(user.getUsername())) {
        Thread.sleep(1000);
        System.err.println("waiting for opponent");
      }
      User userToFight = lobbyRepository.findBattle(user.getUsername());
      List<Card> cardsToFight = cardRepository.getCardsFromUser(
        userToFight.getUsername()
      );
      if (cardsToFight.size() < 4) {
        throw new RuntimeException("Not enough cards in deck");
      }
      int random = Helper.getRandomNumber(0, 4);
      Card card = cards.get(random);
      lobbyRepository.selectCard(username, card.getName());
      lobbyRepository.userIsReady(username);
      while (!lobbyRepository.isUserReady(userToFight.getUsername())) {
        Thread.sleep(1000);
        System.err.println("waiting for opponent to be ready");
      }
      String cardToFightString = lobbyRepository.getCardToFight(
        userToFight.getUsername()
      );
      Card cardToFight = null;
      for (Card c : cardsToFight) {
        if (c.getName().equals(cardToFightString)) {
          cardToFight = c;
          break;
        }
      }
      System.err.println("card to fight: " + cardToFight.getName());
      lobbyRepository.userIsNotReady(username);
      Card winner = Helper.whichCardWins(card, cardToFight);
      System.err.println(
        "card 1: " +
        card.getName() +
        " will fight against " +
        "card 2: " +
        cardToFight.getName()
      );
      System.err.println("winner: " + winner.getName());
      lobbyRepository.userIsReady(username);
      while (!lobbyRepository.isUserReady(userToFight.getUsername())) {
        Thread.sleep(1000);
        System.err.println("waiting for opponent to be ready 2");
      }
      lobbyRepository.leaveLobby(userToFight.getUsername());
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
    return "Battle started";
  }
}
