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

  public static synchronized Card whichCardWins(Card card1, Card card2) {
    if (card1 == null || card2 == null) {
      throw new IllegalArgumentException("One or both cards are null.");
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

  private static Card resolveMonsterFight(Card card1, Card card2) {
    // Pure monster fight, no effect from element types
    if (card1.getDamage() > card2.getDamage()) {
      return card1;
    } else if (card1.getDamage() < card2.getDamage()) {
      return card2;
    } else {
      // Draw - randomly select one of the cards
      return random.nextBoolean() ? card1 : card2;
    }
  }

  private static Card resolveSpellFight(Card card1, Card card2) {
    // Spell vs Spell comparison
    String element1 = extractElement(card1.getName());
    String element2 = extractElement(card2.getName());

    if (isEffectiveAgainst(element1, element2)) {
      return card1;
    } else if (isEffectiveAgainst(element2, element1)) {
      return card2;
    } else {
      // No effect - randomly select one of the cards
      return random.nextBoolean() ? card1 : card2;
    }
  }

  private static Card resolveMonsterSpellFight(Card monster, Card spell) {
    // Monster vs Spell comparison
    String monsterElement = extractElement(monster.getName());
    String spellElement = extractElement(spell.getName());

    if (isEffectiveAgainst(spellElement, monsterElement)) {
      // Spell is effective against monster, so spell wins
      return spell;
    } else if (isEffectiveAgainst(monsterElement, spellElement)) {
      // Monster is effective against spell, so monster wins
      return monster;
    } else {
      // Neither is effective against the other, so compare damage
      if (monster.getDamage() > spell.getDamage()) {
        return monster;
      } else if (spell.getDamage() > monster.getDamage()) {
        return spell;
      } else {
        // It's a draw, so return null or throw an exception
        return null;
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
