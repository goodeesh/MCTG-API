package at.technikum.apps.mtcg.entity;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class Session {
    private int id;
    private String token;
    private String username;
    private Timestamp created;
    private Timestamp expires;
    
    public Session() {
    }
    
    public Session(String token, String username) {
        this.token = token;
        this.username = username;
        this.created = Timestamp.from(Instant.now());
        this.expires = Timestamp.from(Instant.now().plus(Duration.ofHours(24)));
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Timestamp getCreated() {
        return created;
    }
    
    public void setCreated(Timestamp created) {
        this.created = created;
    }
    
    public Timestamp getExpires() {
        return expires;
    }
    
    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }
}
