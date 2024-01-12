package at.technikum.apps.mtcg.entity;

import at.technikum.apps.mtcg.repository.CardRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trading {

  private final CardRepository cardRepository = new CardRepository();
  String id;
  Card card;

  public Trading() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Card getCard() {
    return card;
  }

  @JsonSetter("cardtotrade")
  public void setCardFromString(String card) {
    this.card = cardRepository.getCardById(card);
  }

  public void setCard(Card card) {
    this.card = card;
  }
}
