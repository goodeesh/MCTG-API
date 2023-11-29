package at.technikum.apps.mtcg.controller;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Session;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class SessionsController implements Controller {

    private final SessionService sessionService;

    public SessionsController() {
        this.sessionService = new SessionService();
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/sessions");
    }

    @Override
    public Response handle(Request request) {
        switch (request.getMethod()) {
            case "POST":
                System.err.println("this is a post request in " + request.getRoute() + "... handling it");
                return create(request);
            default:
                break;
        }
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.TEXT_PLAIN);
        response.setBody("sessions controller");

        return response;
    }
        public Response create(Request request) {
        System.err.println(request.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        Session session = null;
        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Optional<Session> sessionOptional = null;
        System.err.println(session);
        try {
            sessionOptional = sessionService.save(user);
        } catch (Exception e) {
            if (e.getMessage().contains("Invalid username/password provided")){
                Response response = new Response();
                response.setStatus(HttpStatus.UNAUTHORIZED);
                response.setContentType(HttpContentType.TEXT_PLAIN);
                response.setBody("Invalid username/password provided");
                return response;
            } else if (e.getMessage().contains("Internal server error")){
                Response response = new Response();
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setContentType(HttpContentType.TEXT_PLAIN);
                response.setBody("Internal server error");
                return response;
            } else {
                Response response = new Response();
                response.setStatus(HttpStatus.CONFLICT);
                response.setContentType(HttpContentType.TEXT_PLAIN);
                response.setBody("Session could not be registered");
                return response;
            }
        }
        if (sessionOptional.isEmpty()) {
            Response response = new Response();
            response.setStatus(HttpStatus.CONFLICT);
            response.setContentType(HttpContentType.TEXT_PLAIN);
            response.setBody("Session could not be registered");
            return response;
        } else{
            session = sessionOptional.get();
            String taskJson = null;
        try {
            taskJson = objectMapper.writeValueAsString(session);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Auth auth = getAuthInstance();
        auth.setToken(session.getToken());
        System.err.println("token: " + auth.getToken());
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(taskJson);

        return response;
        }
    }
}