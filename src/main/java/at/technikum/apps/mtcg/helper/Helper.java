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

  public final String[] transformStringtoStringArray(String string) {
    String[] cardsIds = string.split(",");
    for (int i = 0; i < cardsIds.length; i++) {
      if (i == 0) {
        cardsIds[i] = cardsIds[i].substring(2, cardsIds[i].length() - 1);
      } else if (i == cardsIds.length - 1) {
        cardsIds[i] = cardsIds[i].substring(1, cardsIds[i].length() - 2);
      } else {
        cardsIds[i] = cardsIds[i].substring(1, cardsIds[i].length() - 1);
      }
      System.err.println(cardsIds[i]);
    }
    return cardsIds;
  }
}
