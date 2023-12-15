package at.technikum.apps.mtcg.entity;

import java.util.List;

public class Deck {

  private List<Card> cards;
  private String owner;

  public Deck(List<Card> cards, String owner) {
    this.cards = cards;
    this.owner = owner;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}
