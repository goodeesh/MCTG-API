package at.technikum.apps.mtcg.entity;

public class Trading {

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

  public void setCard(Card card) {
    this.card = card;
  }
}
