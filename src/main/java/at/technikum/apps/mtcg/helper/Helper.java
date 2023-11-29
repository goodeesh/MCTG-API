package at.technikum.apps.mtcg.helper;

import java.util.Optional;

public class Helper {

  public static Optional<String> getSecondParameterRoute(String route) {
    Optional<String> secondParameter = Optional.empty();
    Integer positionOfSecondSlash = route.indexOf("/", 1);
    if (positionOfSecondSlash != -1) {
      secondParameter =
        Optional.of(route.substring(positionOfSecondSlash + 1, route.length()));
    }
    return secondParameter;
  }
}
