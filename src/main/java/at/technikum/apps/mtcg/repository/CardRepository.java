package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardRepository {

  private final Database database;
  private final String SAVE_SQL =
    "INSERT INTO cards(id, name, damage, owner, inDeck) VALUES(?,?,?,?,?)";
  private final String CHANGE_OWNER_SQL =
    "UPDATE cards SET owner = ? WHERE id = ?";
  private final String FIND_BY_ID_SQL =
    "SELECT name,owner,inDeck,damage FROM cards WHERE id = ?";

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
        card.setInDeck(resultSet.getBoolean("inDeck"));
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
}
