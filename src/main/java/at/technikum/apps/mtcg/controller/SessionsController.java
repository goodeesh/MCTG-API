package at.technikum.apps.mtcg.controller;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.apps.mtcg.entity.Session;
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
        Session session = null;
        try {
            session = objectMapper.readValue(request.getBody(), Session.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Optional<Session> userOptional = null;
        // task = toObject(request.getBody(), Task.class);
        System.err.println(session);
        userOptional = sessionService.save(session);
        if (userOptional.isEmpty()) {
            Response response = new Response();
            response.setStatus(HttpStatus.CONFLICT);
            response.setContentType(HttpContentType.TEXT_PLAIN);
            response.setBody("Session for " + session.getUsername() + " could not be registered");
            return response;
        } else{
            session = userOptional.get();
            String taskJson = null;
        try {
            taskJson = objectMapper.writeValueAsString(session);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.CREATED);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(taskJson);

        return response;
        }
    }
}