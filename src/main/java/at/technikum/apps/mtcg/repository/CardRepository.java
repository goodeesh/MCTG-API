package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

  private final Database database;
  private final String SAVE_SQL =
    "INSERT INTO cards(id, name, damage, owner, indeck) VALUES(?,?,?,?,?)";
  private final String CHANGE_OWNER_SQL =
    "UPDATE cards SET owner = ? WHERE id = ?";

  public CardRepository() {
    this.database = new Database();
  }

  public Card[] getAllCards() {
    // Your implementation here
    return null;
  }

  public Card getCardById(String cardId) {
    Card card = null;
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM cards WHERE id = ?"
      );
    ) {
      statement.setString(1, cardId);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        card = new Card();
        card.setId(cardId);
        card.setName(resultSet.getString("name"));
        card.setDamage(resultSet.getInt("damage"));
        card.setOwnerUsername(resultSet.getString("owner"));
        card.setInDeck(resultSet.getBoolean("indeck"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return card;
  }

  public Card saveCard(Card card) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(SAVE_SQL)
    ) {
      statement.setString(1, card.getId());
      statement.setString(2, card.getName());
      statement.setInt(3, card.getDamage());
      statement.setString(4, card.getOwnerUsername());
      statement.setBoolean(5, card.isInDeck());
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return card;
  }

  public void deleteCardById(String cardId) {
    // Your implementation here
  }

  public void changueOwnership(String cardId, String username) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        CHANGE_OWNER_SQL
      )
    ) {
      System.err.println("changing ownership of " + cardId + " to " + username);
      statement.setString(1, username);
      statement.setString(2, cardId);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Card> getCardsFromUser(String username) {
    List<Card> cards = new ArrayList<>();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM cards WHERE owner = ?"
      )
    ) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Card card = new Card();
        card.setId(resultSet.getString("id"));
        card.setName(resultSet.getString("name"));
        card.setDamage(resultSet.getInt("damage"));
        card.setOwnerUsername(resultSet.getString("owner"));
        card.setInDeck(resultSet.getBoolean("indeck"));
        cards.add(card);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cards;
  }

  public List<Card> getCardsFromUserWhereInDeckTrue(String username) {
    List<Card> cards = new ArrayList<>();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM cards WHERE owner = ? AND indeck = ?"
      )
    ) {
      statement.setString(1, username);
      statement.setBoolean(2, true);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Card card = new Card();
        card.setId(resultSet.getString("id"));
        card.setName(resultSet.getString("name"));
        card.setDamage(resultSet.getInt("damage"));
        card.setOwnerUsername(resultSet.getString("owner"));
        card.setInDeck(resultSet.getBoolean("indeck"));
        cards.add(card);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cards;
  }

  public boolean setCardsInDeckToTrue(String username, String[] cardsIds) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "UPDATE cards SET indeck = ? WHERE owner = ?"
      )
    ) {
      statement.setBoolean(1, false);
      statement.setString(2, username);
      statement.executeUpdate();
      try (
        PreparedStatement statement2 = connection.prepareStatement(
          "UPDATE cards SET indeck = ? WHERE id = ?"
        );
      ) {
        Thread.sleep(1000);
        for (String cardId : cardsIds) {
          System.err.println("trying to set card " + cardId + " to true");
          statement2.setBoolean(1, true);
          statement2.setString(2, cardId);
          statement2.executeUpdate();
        }
      } catch (InterruptedException e) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
