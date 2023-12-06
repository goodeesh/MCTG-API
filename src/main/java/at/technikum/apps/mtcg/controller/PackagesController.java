package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PackagesController implements Controller {

  @Override
  public boolean supports(String route) {
    return route.equals("/packages");
  }

  @Override
  public Response handle(Request request) {
    switch (request.getMethod()) {
      case "POST":
        System.err.println(
          "this is a post request in " + request.getRoute() + "... handling it"
        );
        return create(request);
      default:
        break;
    }
    Response response = new Response();
    response.setStatus(HttpStatus.OK);
    response.setContentType(HttpContentType.TEXT_PLAIN);
    response.setBody("packages controller");

    return response;
  }

  public Response create(Request request) {
    ObjectMapper objectMapper = new ObjectMapper();
    Card[] cards = null;
    System.err.println(request.getBody());
    try {
      cards = objectMapper.readValue(request.getBody(), Card[].class);
    } catch (JsonProcessingException e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    System.err.println(cards);
    return new Response(HttpStatus.OK, "all good");
  }
  /* public Response create(Request request) {
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
        taskJson = objectMapper.writeValueAsString(userOptional.get());
        return new Response(HttpStatus.CREATED, taskJson);
      } catch (JsonProcessingException e) {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
    }
  } */
}
