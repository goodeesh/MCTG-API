package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public interface Controller {

    boolean supports(String route);

    Response handle(Request request);
    
    default Auth getAuthInstance() {
        return Auth.getInstance();
    }
}
