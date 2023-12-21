package at.technikum.apps.mtcg.entity;

public class Stats {

  private Integer wins;
  private Integer losses;
  private Integer elo;

  public Stats(Integer wins, Integer losses, Integer elo) {
    this.wins = wins;
    this.losses = losses;
    this.elo = elo;
  }

  public Integer getWins() {
    return wins;
  }

  public void setWins(Integer wins) {
    this.wins = wins;
  }

  public Integer getLosses() {
    return losses;
  }

  public void setLosses(Integer losses) {
    this.losses = losses;
  }

  public Integer getElo() {
    return elo;
  }

  public void setElo(Integer elo) {
    this.elo = elo;
  }
}
