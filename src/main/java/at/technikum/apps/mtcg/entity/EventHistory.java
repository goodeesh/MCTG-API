package at.technikum.apps.mtcg.entity;

public class EventHistory {

  String id;
  String type;
  String[] users;
  String date;
  String log;

  public EventHistory(
    String id,
    String type,
    String[] users,
    String date,
    String log
  ) {
    this.id = id;
    this.type = type;
    this.users = users;
    this.date = date;
    this.log = log;
  }

  public void setUsers(String[] users) {
    this.users = users;
  }

  public String[] getUsers() {
    return users;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getLog() {
    return log;
  }

  public void setLog(String log) {
    this.log = log;
  }
}
