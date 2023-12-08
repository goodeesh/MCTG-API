package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Package;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class PackageRepository {

  private final Database database;
  private final String SAVE_SQL = "INSERT INTO packages(id, cards) VALUES(?,?)";

  public PackageRepository() {
    this.database = new Database();
  }

  public Package create(Package packageToCreate) {
    String cardIds = "";
    for (int i = 0; i < packageToCreate.getCards().length; i++) {
      cardIds += packageToCreate.getCards()[i].getId();
      if (i < packageToCreate.getCards().length - 1) {
        cardIds += ",";
      }
    }
    CardRepository cardRepository = new CardRepository();
    try {
      for (int i = 0; i < packageToCreate.getCards().length; i++) {
        packageToCreate.getCards()[i].setOwnerUsername("admin");
        cardRepository.saveCard(packageToCreate.getCards()[i]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    packageToCreate.setId(UUID.randomUUID().toString());
    try (
      Connection connection = database.getConnection();
      PreparedStatement statement = connection.prepareStatement(SAVE_SQL)
    ) {
      statement.setString(1, packageToCreate.getId());
      statement.setString(2, cardIds);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return packageToCreate;
  }
}
