package at.technikum.apps.mtcg.entity;

public class User {

  private String id;
  private String username;
  private String password;
  private Integer money;
  private String name;
  private String bio;
  private String image;

  public User() {}

  public User(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public User(
    String id,
    String username,
    String password,
    Integer money,
    String name,
    String bio,
    String image
  ) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.money = money;
    this.name = name;
    this.bio = bio;
    this.image = image;
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Integer getMoney() {
    return money;
  }

  public void setMoney(Integer money) {
    this.money = money;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
