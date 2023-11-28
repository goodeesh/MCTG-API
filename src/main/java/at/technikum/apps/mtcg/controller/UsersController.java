package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersController implements Controller {

    private final UserService userService;

    public UsersController() {
        this.userService = new UserService();
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/users");
    }

    @Override
    public Response handle(Request request) {
        String route = request.getRoute();
        String userId = "";
        Integer positionOfSecondSlash = route.indexOf("/", 1);
        System.err.println(positionOfSecondSlash);
        if (positionOfSecondSlash != -1) {
            userId = route.substring(positionOfSecondSlash + 1, route.length());
            System.err.println(userId);

        }
        if (positionOfSecondSlash == -1) { // no username set
            switch (request.getMethod()) {
                case "GET":
                    System.err.println("this is a get request in " + request.getRoute() + "... handling it");
                    return findAll(request);
                case "POST":
                    System.err.println("this is a post request in " + request.getRoute() + "... handling it");
                    return create(request);
                default:
                    break;
            }
        } else { // username was set
            switch (request.getMethod()) {
                case "GET":
                    System.err.println(request.getBody());
                    System.err.println(
                            "this is a GET request in " + request.getRoute() + "... handling it for user " + userId);
                    return find(userId);
                case "PUT":
                    System.err.println(
                            "this is a PUT request in " + request.getRoute() + "... handling it for user " + userId);
                    return update(userId, request, "admin");
                default:
                    break;
            }
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.TEXT_PLAIN);

        response.setBody("servus! from users controller");

        return response;
    }

    public Response update(String userId, Request request, String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        System.err.println("update user id: " + userId);

        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Optional<User> userOptional = null;
        try {
            userOptional = userService.update(userId, user, username);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        if (userOptional.isEmpty()) {
            Response response = new Response();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(HttpContentType.TEXT_PLAIN);
            response.setBody("User with id " + user.getId() + " not found");
            return response;
        }
        String taskJson = null;
        try {
            taskJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Response response = new Response();
        // THOUGHT: better status 201 Created
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(taskJson);

        return response;

        // return json(task);
    }

    public Response create(Request request) {
        System.err.println(request.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Optional<User> userOptional = null;
        // task = toObject(request.getBody(), Task.class);
        System.err.println(user);
        userOptional = userService.save(user);
        if (userOptional.isEmpty()) {
            Response response = new Response();
            response.setStatus(HttpStatus.CONFLICT);
            response.setContentType(HttpContentType.TEXT_PLAIN);
            response.setBody("Username: " + user.getUsername() + " cannot be registered");
            return response;
        } else{
            user = userOptional.get();
            String taskJson = null;
        try {
            taskJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.CREATED);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(taskJson);

        return response;
        }
        

        // return json(task);
    }

    public Response findAll(Request request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String usersJson = null;
        try {
            usersJson = objectMapper.writeValueAsString(userService.findAll());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(usersJson);

        return response;
    }

    public Response find(String userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<User> userOptional = null;
        try {
            userOptional = userService.find(userId);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        if (userOptional.isEmpty()) {
            Response response = new Response();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(HttpContentType.TEXT_PLAIN);
            response.setBody("User with id " + userId + " not found");
            return response;
        } else {
            String userJson = null;
            try {
                userJson = objectMapper.writeValueAsString(userOptional.get());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Response response = new Response();
            response.setStatus(HttpStatus.OK);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody(userJson);
            return response;
        }
    }
}
