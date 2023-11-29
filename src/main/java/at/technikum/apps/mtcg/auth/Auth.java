package at.technikum.apps.mtcg.auth;

public class Auth {
    private static final Auth instance = new Auth();
    private String token;

    private Auth() {
        // Private constructor to prevent external instantiation
    }

    public static Auth getInstance() {
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
