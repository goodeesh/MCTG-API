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
        tradings.add(trading);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tradings;
  }
}
