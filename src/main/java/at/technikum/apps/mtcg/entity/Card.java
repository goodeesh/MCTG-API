package at.technikum.apps.mtcg.entity;

public class Card {

  private String name;
  private String id;
  private String ownerUsername;
  private boolean inDeck;
  private int damage;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOwnerUsername() {
    return ownerUsername;
  }

  public void setOwnerUsername(String ownerUsername) {
    this.ownerUsername = ownerUsername;
  }

  public boolean isInDeck() {
    return inDeck;
  }

  public void setInDeck(boolean inDeck) {
    this.inDeck = inDeck;
  }

  public Integer getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }
}
