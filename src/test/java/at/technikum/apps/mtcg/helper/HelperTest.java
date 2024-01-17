package at.technikum.apps.mtcg.helper;

import static org.junit.jupiter.api.Assertions.*;

import at.technikum.apps.mtcg.entity.Card;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HelperTest {

  @Test
  void getRandomNumber() {
    int random = Helper.getRandomNumber(1, 10);
    assertTrue(random >= 1 && random < 10);
  }

  @Test
  void getTypeFromCard() {
    assertEquals("spell", Helper.getTypeFromCard("spellCard"));
    assertEquals("monster", Helper.getTypeFromCard("monsterCard"));
  }

  @Test
  void whichCardWins() {
    Card card1 = Mockito.mock(Card.class);
    Mockito.when(card1.getName()).thenReturn("monsterCard");
    Mockito.when(card1.getDamage()).thenReturn(10);

    Card card2 = Mockito.mock(Card.class);
    Mockito.when(card2.getName()).thenReturn("spellCard");
    Mockito.when(card2.getDamage()).thenReturn(5);

    assertEquals(Optional.of(card1), Helper.whichCardWins(card1, card2));
  }

  @Test
  void getSecondParameterRoute() {
    assertEquals(
      Optional.of("second"),
      Helper.getSecondParameterRoute("/first/second")
    );
    assertEquals(Optional.empty(), Helper.getSecondParameterRoute("/first"));
  }

  @Test
  void transformStringtoStringArray() {
    Helper helper = new Helper();
    String[] result = helper.transformStringtoStringArray(
      "\"card1\",\"card2\",\"card3\""
    );
    assertArrayEquals(new String[] { "card1", "card2", "card3" }, result);
  }

  @Test
  void getRandomNumber_OutOfRange() {
    assertThrows(
      IllegalArgumentException.class,
      () -> Helper.getRandomNumber(10, 1)
    );
  }

  @Test
  void getTypeFromCard_InvalidCardName() {
    assertEquals("monster", Helper.getTypeFromCard("invalidCard"));
  }

  @Test
  void whichCardWins_NullCard() {
    Card card = Mockito.mock(Card.class);
    assertThrows(
      IllegalArgumentException.class,
      () -> Helper.whichCardWins(card, null)
    );
  }

  @Test
  void whichCardWins_CardHasNoName() {
    Card card1 = Mockito.mock(Card.class);
    Mockito.when(card1.getName()).thenReturn(null);
    Mockito.when(card1.getDamage()).thenReturn(10);

    Card card2 = Mockito.mock(Card.class);
    Mockito.when(card2.getName()).thenReturn("validCard");
    Mockito.when(card2.getDamage()).thenReturn(5);

    assertThrows(
      IllegalArgumentException.class,
      () -> Helper.whichCardWins(card1, card2)
    );
  }

  @Test
  void getSecondParameterRoute_InvalidRoute() {
    assertEquals(Optional.empty(), Helper.getSecondParameterRoute("/first"));
  }

  @Test
  void transformStringtoStringArray_InvalidString() {
    Helper helper = new Helper();
    String[] result = helper.transformStringtoStringArray(
      "\"card1\",\"card2\",\"card3\""
    );
    assertNotEquals(
      new String[] { "invalidCard1", "invalidCard2", "invalidCard3" },
      result
    );
  }
}
