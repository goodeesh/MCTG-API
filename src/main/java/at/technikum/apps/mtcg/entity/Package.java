package at.technikum.apps.mtcg.entity;

public class Package {

  private Card[] cards;
  private String id;

  public Package() {}

  public Package(Card[] cards, String id) {
    this.cards = cards;
    this.id = id;
  }

  public Card[] getCards() {
    return cards;
  }

  public void setCards(Card[] cards) {
    this.cards = cards;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
