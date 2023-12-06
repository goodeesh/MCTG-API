package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CardRepository {

  private final Database database;
  private final String SAVE_SQL =
    "INSERT INTO cards(id, name, damage, owner, inDeck) VALUES(?,?,?,?,?)";

  public CardRepository() {
    this.database = new Database();
  }

  public Card[] getAllCards() {
    // Your implementation here
    return null;
  }

  public Card getCardById(String cardId) {
    // Your implementation here
    return null;
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
}
