package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.repository.historyRepository;
import java.util.List;
import java.util.UUID;

public class TransactionService {

  private final PackageRepository packageRepository;
  private final CardRepository cardRepository;
  private final UserRepository userRepository;
  private final historyRepository historyRepository;

  public TransactionService() {
    this.packageRepository = new PackageRepository();
    this.cardRepository = new CardRepository();
    this.userRepository = new UserRepository();
    this.historyRepository = new historyRepository();
  }

  public void buy(String username) {
    List<Package> packages = packageRepository.getAll();
    int numberOfPackages = packages.size();
    int randomIndex = (int) (Math.random() * numberOfPackages);
    Package packageToBuy = null;
    if (numberOfPackages > 0) {
      packageToBuy = packages.get(randomIndex);
    } else {
      throw new RuntimeException("Package not found");
    }
    if (!userRepository.updateMoney(username, packageToBuy.getPrice())) {
      throw new RuntimeException("Not enough money");
    } else {
      String packageId = packages.get(randomIndex).getId();
      for (int i = 0; i < packageToBuy.getCards().length; i++) {
        cardRepository.changueOwnership(
          packageToBuy.getCards()[i].getId(),
          username
        );
      }
      //String id, String type, String[] users, String result
      historyRepository.saveEvent(
        UUID.randomUUID().toString(),
        "buy",
        new String[] { username },
        "User " +
        username +
        " bought a package for " +
        packageToBuy.getPrice() +
        " coins"
      );
      packageRepository.delete(packageId);
    }
  }
}
