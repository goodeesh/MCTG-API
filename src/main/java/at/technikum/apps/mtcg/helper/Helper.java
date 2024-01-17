package at.technikum.apps.mtcg.helper;

import at.technikum.apps.mtcg.entity.Card;
import java.util.Optional;
import java.util.Random;

public class Helper {

  private static final long SEED = 12345L;
  private static final Random random = new Random(SEED);

  public static int getRandomNumber(int min, int max) {
    return random.nextInt(max - min) + min;
  }

  public static String getTypeFromCard(String cardName) {
    System.err.println("the name of the card is: " + cardName);
    if (cardName.toLowerCase().contains("spell")) {
      return "spell";
    } else {
      return "monster";
    }
  }

  public static synchronized Optional<Card> whichCardWins(
    Card card1,
    Card card2
  ) {
    if (card1 == null || card2 == null) {
      throw new IllegalArgumentException("One or both cards are null.");
    }
    if (card1.getName() == null || card2.getName() == null) {
      throw new IllegalArgumentException("One or both cards have no name.");
    }
    if (
      card1.getDamage() < 0 ||
      card2.getDamage() < 0 ||
      card1.getDamage() == 0 ||
      card2.getDamage() == 0 ||
      card1.getDamage() == null ||
      card2.getDamage() == null
    ) {
      throw new IllegalArgumentException(
        "One or both cards have no damage or damage is negative."
      );
    }

    boolean isCard1Monster = isMonster(card1);
    boolean isCard2Monster = isMonster(card2);

    if (isCard1Monster && isCard2Monster) {
      // Pure monster fight, no effect from element types
      return resolveMonsterFight(card1, card2);
    } else if (!isCard1Monster && !isCard2Monster) {
      // Spell vs Spell comparison
      return resolveSpellFight(card1, card2);
    } else {
      // Monster vs Spell comparison
      return resolveMonsterSpellFight(card1, card2);
    }
  }

  private static boolean isMonster(Card card) {
    return !card.getName().toLowerCase().contains("spell");
  }

  private static Optional<Card> resolveMonsterFight(Card card1, Card card2) {
    // Pure monster fight, no effect from element types
    if (card1.getDamage() > card2.getDamage()) {
      return Optional.of(card1);
    } else if (card1.getDamage() < card2.getDamage()) {
      return Optional.of(card2);
    } else {
      // Draw - randomly select one of the cards
      if (getRandomNumber(0, 1) == 0) {
        return Optional.of(card1);
      } else {
        return Optional.of(card2);
      }
    }
  }

  private static Optional<Card> resolveSpellFight(Card card1, Card card2) {
    // Spell vs Spell comparison
    String element1 = extractElement(card1.getName());
    String element2 = extractElement(card2.getName());

    double card1Damage = card1.getDamage();
    double card2Damage = card2.getDamage();

    if (isEffectiveAgainst(element1, element2)) {
      // card1 is effective against card2, so card1's damage is doubled
      card1Damage *= 2;
    } else if (isEffectiveAgainst(element2, element1)) {
      // card2 is effective against card1, so card2's damage is doubled
      card2Damage *= 2;
    }

    // Compare damage
    if (card1Damage > card2Damage) {
      return Optional.of(card1);
    } else if (card1Damage < card2Damage) {
      return Optional.of(card2);
    } else {
      if (getRandomNumber(0, 1) == 0) {
        return Optional.of(card1);
      } else {
        return Optional.of(card2);
      }
    }
  }

  private static Optional<Card> resolveMonsterSpellFight(
    Card monster,
    Card spell
  ) {
    // Monster vs Spell comparison
    String monsterElement = extractElement(monster.getName());
    String spellElement = extractElement(spell.getName());

    double monsterDamage = monster.getDamage();
    double spellDamage = spell.getDamage();

    if (isEffectiveAgainst(spellElement, monsterElement)) {
      // Spell is effective against monster, so spell damage is doubled
      spellDamage *= 2;
    } else if (isEffectiveAgainst(monsterElement, spellElement)) {
      // Monster is effective against spell, so monster damage is doubled
      monsterDamage *= 2;
    }

    // Compare damage
    if (monsterDamage > spellDamage) {
      return Optional.of(monster);
    } else if (spellDamage > monsterDamage) {
      return Optional.of(spell);
    } else {
      if (getRandomNumber(0, 1) == 0) {
        return Optional.of(monster);
      } else {
        return Optional.of(spell);
      }
    }
  }

  private static boolean isEffectiveAgainst(String element1, String element2) {
    return (
      (element1.equals("water") && element2.equals("fire")) ||
      (element1.equals("fire") && element2.equals("normal")) ||
      (element1.equals("normal") && element2.equals("water"))
    );
  }

  private static String extractElement(String cardName) {
    if (cardName.toLowerCase().contains("water")) {
      return "water";
    } else if (cardName.toLowerCase().contains("fire")) {
      return "fire";
    } else {
      return "normal";
    }
  }

  public static Optional<String> getSecondParameterRoute(String route) {
    Optional<String> secondParameter = Optional.empty();
    Integer positionOfSecondSlash = route.indexOf("/", 1);
    if (positionOfSecondSlash != -1) {
      secondParameter =
        Optional.of(route.substring(positionOfSecondSlash + 1, route.length()));
    }
    return secondParameter;
  }

  // old version, problems with test!
  // public final String[] transformStringtoStringArray(String string) {
  //   String[] cardsIds = string.split(",");
  //   for (int i = 0; i < cardsIds.length; i++) {
  //     if (i == 0) {
  //       cardsIds[i] = cardsIds[i].substring(2, cardsIds[i].length() - 1);
  //     } else if (i == cardsIds.length - 1) {
  //       cardsIds[i] = cardsIds[i].substring(1, cardsIds[i].length() - 2);
  //     } else {
  //       cardsIds[i] = cardsIds[i].substring(1, cardsIds[i].length() - 1);
  //     }
  //     System.err.println(cardsIds[i]);
  //   }
  //   return cardsIds;
  // }
  public final String[] transformStringtoStringArray(String string) {
    // Remove the leading and trailing brackets and split the string into an array
    String[] cardsIds = string.substring(1, string.length() - 1).split(",");

    // Remove the quotes and whitespace from each element
    for (int i = 0; i < cardsIds.length; i++) {
      cardsIds[i] = cardsIds[i].replaceAll("^\\s*\"|\"\\s*$", "");
    }

    return cardsIds;
  }
}
