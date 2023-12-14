package at.technikum.apps.mtcg.controller;

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
    if (secondParameter.isEmpty()) { // no username set
      switch (request.getMethod()) {
        case "GET":
          return findAll(request);
        case "POST":
          return create(request);
        default:
          break;
      }
    } else { // username was set
      String username = secondParameter.get();
      switch (request.getMethod()) {
        case "GET":
          return find(request);
        case "PUT":
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
    ObjectMapper objectMapper = new ObjectMapper();
    User user = null;
    String authorizationToken = request.getAuthorization();
    try {
      user = objectMapper.readValue(request.getBody(), User.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Optional<User> userOptional = null;
    try {
      userOptional =
        userService.update(usernameToUpdate, user, authorizationToken);
    } catch (RuntimeException e) {
      if (e.getMessage().equals("Not allowed to do this")) {
        return new Response(HttpStatus.UNAUTHORIZED, "Not allowed to do this");
      } else if (e.getMessage().equals("User not found")) {
        return new Response(HttpStatus.NOT_FOUND, "User not found");
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
    //create User Object from request
    ObjectMapper objectMapper = new ObjectMapper();
    User user = null;
    try {
      user = objectMapper.readValue(request.getBody(), User.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    ///////////////////////////////////////
    //save user into database
    User userOptional = null;
    try {
      userOptional = userService.save(user);
      try {
        String taskJson = objectMapper.writeValueAsString(userOptional);
        return new Response(HttpStatus.CREATED, taskJson);
      } catch (JsonProcessingException e) {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
    } catch (RuntimeException e) {
      System.err.println(e.getMessage());
      if (e.getMessage().equals("Username already exists")) {
        return new Response(HttpStatus.CONFLICT, "Username is already in use");
      } else {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Internal server error"
        );
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

  public Response find(Request request) {
    ObjectMapper objectMapper = new ObjectMapper();
    String username = Helper.getSecondParameterRoute(request.getRoute()).get();
    String token = request.getAuthorization();
    User userOptional = null;
    try {
      userOptional = userService.find(username, token);
    } catch (RuntimeException e) {
      if (e.getMessage().contains("Not allowed to do this")) {
        return new Response(HttpStatus.UNAUTHORIZED, "Not allowed to do this");
      } else if (e.getMessage().contains("User not found")) {
        return new Response(HttpStatus.NOT_FOUND, "User not found");
      } else {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
    }
    String userJson = null;
    try {
      userJson = objectMapper.writeValueAsString(userOptional);
    } catch (JsonProcessingException e) {
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
    return new Response(HttpStatus.OK, userJson);
  }
}
