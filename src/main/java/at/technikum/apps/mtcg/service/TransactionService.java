package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.PackageRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import java.util.List;

public class TransactionService {

  private final PackageRepository packageRepository;
  private final CardRepository cardRepository;
  private final UserRepository userRepository;

  public TransactionService() {
    this.packageRepository = new PackageRepository();
    this.cardRepository = new CardRepository();
    this.userRepository = new UserRepository();
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
      packageRepository.delete(packageId);
    }
  }
}
