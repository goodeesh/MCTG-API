package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.repository.PackageRepository;

public class PackageService {

  private final PackageRepository packageRepository;

  public PackageService() {
    this.packageRepository = new PackageRepository();
  }

  public Package create(Package packageToCreate) {
    return packageRepository.create(packageToCreate);
  }
}
