package at.technikum.apps.mtcg.entity;

public class Package {

  private Card[] cards;
  private String id;
  private int price;

  public Package(Card[] cards, String id) {
    this.cards = cards;
    this.id = id;
    this.price = 15;
  }

  public Package() {
    this.price = 15;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
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
