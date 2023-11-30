package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.helper.Helper;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

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
    new Helper();
    Optional<String> secondParameter = Helper.getSecondParameterRoute(route);
    Integer positionOfSecondSlash = route.indexOf("/", 1);
    System.err.println(positionOfSecondSlash);
    if (secondParameter.isEmpty()) { // no username set
      switch (request.getMethod()) {
        case "GET":
          System.err.println(
            "this is a get request in " + request.getRoute() + "... handling it"
          );
          return findAll(request);
        case "POST":
          System.err.println(
            "this is a post request in " +
            request.getRoute() +
            "... handling it"
          );
          return create(request);
        default:
          break;
      }
    } else { // username was set
      String username = secondParameter.get();
      switch (request.getMethod()) {
        case "GET":
          System.err.println(request.getBody());
          System.err.println(
            "this is a GET request in " +
            request.getRoute() +
            "... handling it for user " +
            username
          );
          return find(username);
        case "PUT":
          System.err.println(
            "this is a PUT request in " +
            request.getRoute() +
            "... handling it for user " +
            username
          );
          return update(username, request);
        default:
          break;
      }
    }
    return new Response(
      HttpStatus.METHOD_NOT_ALLOWED,
      "This request is not supported"
    );
  }

  public Response update(String usernameToUpdate, Request request) {
    System.err.println(
      "this is the Authorization token: " + request.getAuthorization()
    );
    ObjectMapper objectMapper = new ObjectMapper();
    User user = null;
    String authorizationToken = request.getAuthorization();
    System.err.println(authorizationToken);
    if (authorizationToken == "") {
      return new Response(HttpStatus.UNAUTHORIZED, "Not allowed to do this");
    }
    try {
      user = objectMapper.readValue(request.getBody(), User.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Optional<User> userOptional = null;
    try {
      userOptional =
        userService.update(usernameToUpdate, user, authorizationToken);
    } catch (NumberFormatException e) {
      if (
        e.getMessage().equals("Session not found") ||
        e.getMessage().equals("Session expired") ||
        e.getMessage().equals("Not allowed to do this")
      ) {
        return new Response(HttpStatus.UNAUTHORIZED, "Not allowed to do this");
      } else if (e.getMessage().equals("Something went wrong")) {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      } else {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
    }
    try {
      String taskJson = objectMapper.writeValueAsString(userOptional.get());
      return new Response(HttpStatus.OK, taskJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public Response create(Request request) {
    ObjectMapper objectMapper = new ObjectMapper();
    User user = null;
    try {
      user = objectMapper.readValue(request.getBody(), User.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Optional<User> userOptional = null;
    userOptional = userService.save(user);
    if (userOptional.isEmpty()) {
      return new Response(
        HttpStatus.CONFLICT,
        "Username " + user.getUsername() + " is already in use"
      );
    } else {
      String taskJson = null;
      try {
        taskJson = objectMapper.writeValueAsString(userOptional);
        return new Response(HttpStatus.CREATED, taskJson);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public Response findAll(Request request) {
    ObjectMapper objectMapper = new ObjectMapper();
    String usersJson = null;
    try {
      usersJson = objectMapper.writeValueAsString(userService.findAll());
    } catch (JsonProcessingException e) {
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
    return new Response(HttpStatus.OK, usersJson);
  }

  public Response find(String username) {
    ObjectMapper objectMapper = new ObjectMapper();
    Optional<User> userOptional = null;
    try {
      userOptional = userService.find(username);
    } catch (NumberFormatException e) {
      throw new RuntimeException(e);
    }
    if (userOptional.isEmpty()) {
      return new Response(
        HttpStatus.NOT_FOUND,
        "User " + username + " not found"
      );
    } else {
      String userJson = null;
      try {
        userJson = objectMapper.writeValueAsString(userOptional.get());
      } catch (JsonProcessingException e) {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
      return new Response(HttpStatus.OK, userJson);
    }
  }
}
