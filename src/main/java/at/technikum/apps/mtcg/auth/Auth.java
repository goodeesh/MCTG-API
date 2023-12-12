package at.technikum.apps.mtcg.auth;

public class Auth {

  public Boolean hasAccess(String username, String token) {
    int start = token.indexOf("Bearer ") + "Bearer ".length();
    int end = token.indexOf("-mtcgToken");
    if (start == -1 || end == -1 || end < start) {
      return false;
    }
    String usernameFromToken = token.substring(start, end);
    if (
      username.equals(usernameFromToken) || usernameFromToken.equals("admin")
    ) {
      return true;
    }
    return false;
  }

  public String extractUsernameFromToken(String token) {
    int start = token.indexOf("Bearer ") + "Bearer ".length();
    int end = token.indexOf("-mtcgToken");
    if (start == -1 || end == -1 || end < start) {
      return null;
    }
    return token.substring(start, end);
  }
}
