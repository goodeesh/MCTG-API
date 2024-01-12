package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Trading;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TradingRepository {

  private final Database database = new Database();
  private final CardRepository cardRepository = new CardRepository();

  public TradingRepository() {}

  public List<Trading> findAll() {
    List<Trading> tradings = new ArrayList<>();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * from tradings"
      );
      ResultSet resultSet = statement.executeQuery();
    ) {
      while (resultSet.next()) {
        Trading trading = new Trading();
        trading.setId(resultSet.getString("id"));
        trading.setCard(
          cardRepository.getCardById(resultSet.getString("cardId"))
        );
        trading.setMinimumDamage(resultSet.getInt("minimumdamage"));
        trading.setType(resultSet.getString("type"));
        tradings.add(trading);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tradings;
  }

  public Boolean isTradingIdPosted(String id) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * from tradings where id = ?"
      )
    ) {
      statement.setString(1, id);
      ResultSet resultSet = statement.executeQuery();
      return resultSet.next();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public Trading save(Trading trading) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO tradings (id, cardId, minimumdamage, type) VALUES (?, ?, ?, ?)"
      );
    ) {
      statement.setString(1, trading.getId());
      statement.setString(2, trading.getCard().getId());
      statement.setInt(3, trading.getMinimumDamage());
      statement.setString(4, trading.getType());
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return trading;
  }

  public Boolean deleteById(String id) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "DELETE FROM tradings WHERE id = ?"
      );
    ) {
      statement.setString(1, id);
      statement.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public Trading findById(String id) {
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM tradings WHERE id = ?"
      );
    ) {
      statement.setString(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        Trading trading = new Trading();
        trading.setId(resultSet.getString("id"));
        trading.setCard(
          cardRepository.getCardById(resultSet.getString("cardId"))
        );
        trading.setMinimumDamage(resultSet.getInt("minimumdamage"));
        trading.setType(resultSet.getString("type"));
        return trading;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Boolean executeTrade(String tradeId, String cardId) {
    Trading trading = this.findById(tradeId);
    String tradeOwner = trading.getCard().getOwnerUsername();
    String cardOwner = cardRepository.getCardById(cardId).getOwnerUsername();
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement1 = connection.prepareStatement(
        "UPDATE cards SET owner = ? WHERE id = ?"
      );
      PreparedStatement statement2 = connection.prepareStatement(
        "UPDATE cards SET owner = ? WHERE id = ?"
      );
      PreparedStatement statement3 = connection.prepareStatement(
        "DELETE FROM tradings WHERE id = ?"
      );
    ) {
      statement1.setString(1, tradeOwner);
      statement1.setString(2, cardId);
      statement1.executeUpdate();

      statement2.setString(1, cardOwner);
      statement2.setString(2, trading.getCard().getId());
      statement2.executeUpdate();

      statement3.setString(1, tradeId);
      statement3.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    System.err.println("executeTrade");
    return true;
  }
}
