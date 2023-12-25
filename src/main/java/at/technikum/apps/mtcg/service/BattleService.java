package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.helper.Helper;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.LobbyRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.repository.historyRepository;
import java.lang.Thread;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class BattleService {

  Auth auth = new Auth();
  private UserRepository userRepository = new UserRepository();
  private CardRepository cardRepository = new CardRepository();
  private LobbyRepository lobbyRepository = new LobbyRepository();
  private historyRepository historyRepository = new historyRepository();

  private synchronized String performBattle(
    List<Card> cards,
    List<Card> cardsToFight,
    String username,
    User userToFight
  ) throws InterruptedException {
    if (
      lobbyRepository.isUserReady(username) &&
      lobbyRepository.isUserReady(userToFight.getUsername())
    ) {
      System.err.println("Battle already happened in the other thread");
      lobbyRepository.leaveLobby(username);
      lobbyRepository.leaveLobby(userToFight.getUsername());
      return null;
    } else {
      System.err.println("cards size: " + cards.size());
      System.err.println("cards to fight size: " + cardsToFight.size());
      while (cards.size() > 0 && cardsToFight.size() > 0) {
        int random = cards.size() == 1
          ? 0
          : Helper.getRandomNumber(0, cards.size() - 1);
        Card card = cards.get(random);
        lobbyRepository.selectCard(username, card.getName());
        int randomOponent = cardsToFight.size() == 1
          ? 0
          : Helper.getRandomNumber(0, cardsToFight.size() - 1);
        Card cardToFight = cardsToFight.get(randomOponent);
        Optional<Card> winner = Helper.whichCardWins(card, cardToFight);
        if (winner.isEmpty()) {
          System.err.println("there is no winner");
        } else if (winner.get().getName().equals(card.getName())) {
          System.err.println("card " + card.getName() + " won");
          if (cardsToFight.size() == 1) {
            cardsToFight = new CopyOnWriteArrayList<>();
          }
          boolean removed = cardsToFight.remove(cardToFight);
          System.err.println(
            "Card " +
            cardToFight.getName() +
            " removal from cardsToFight was " +
            (removed ? "successful" : "unsuccessful")
          );
          System.err.println("cardsToFight size: " + cardsToFight.size());
        } else {
          if (cards.size() == 1) {
            cards = new CopyOnWriteArrayList<>();
          }
          System.err.println("card " + cardToFight.getName() + " won");
          boolean removed = cards.remove(card);
          System.err.println(
            "Card " +
            card.getName() +
            " removal from cards was " +
            (removed ? "successful" : "unsuccessful")
          );
          System.err.println("cards size: " + cards.size());
        }
      }
      lobbyRepository.userIsReady(username);
      lobbyRepository.userIsReady(userToFight.getUsername());
      if (cards.size() > 0) {
        return username;
      } else {
        return userToFight.getUsername();
      }
    }
  }

  public String createBattleIdForThreads(String token) {
    String battleId = UUID.randomUUID().toString();
    return startBattle(token, battleId);
  }

  public String startBattle(String token, String battleId) {
    String winner = null;
    String[] users;
    String type = "battle";
    String username = auth.extractUsernameFromToken(token);
    if (token == null || token.isEmpty() || token.equals("")) {
      throw new RuntimeException("Not allowed to do this");
    }
    User user = userRepository.find(auth.extractUsernameFromToken(token)).get();
    List<Card> cards = cardRepository.getCardsFromUserWhereInDeckTrue(
      user.getUsername()
    );
    if (cards.size() != 4) {
      throw new RuntimeException("Deck needs to have 4 cards");
    }
    try {
      while (lobbyRepository.isLobbyEmpty(user.getUsername())) {
        Thread.sleep(1000);
        System.err.println("waiting for opponent");
      }
      User userToFight = lobbyRepository.findBattle(user.getUsername());
      users = new String[] { username, userToFight.getUsername() };
      List<Card> cardsToFight = cardRepository.getCardsFromUserWhereInDeckTrue(
        userToFight.getUsername()
      );
      if (cardsToFight.size() != 4) {
        throw new RuntimeException("Deck needs to have 4 cards");
      }
      winner = performBattle(cards, cardsToFight, username, userToFight);
      lobbyRepository.userIsReady(username);
      lobbyRepository.userIsReady(userToFight.getUsername());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Something went wrong");
    }
    if (winner != null) historyRepository.saveEvent(
      battleId,
      type,
      users,
      winner
    );
    return "Battle finished";
  }
}
