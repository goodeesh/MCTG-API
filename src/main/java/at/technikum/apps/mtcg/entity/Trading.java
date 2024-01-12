package at.technikum.apps.mtcg.entity;

import at.technikum.apps.mtcg.repository.CardRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trading {

  private final CardRepository cardRepository = new CardRepository();
  String id;
  Card card;
  String type;
  Integer minimumdamage;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getMinimumDamage() {
    return minimumdamage;
  }

  @JsonSetter("minimumdamage")
  public void setMinimumDamage(Integer minimumdamage) {
    this.minimumdamage = minimumdamage;
  }
}
