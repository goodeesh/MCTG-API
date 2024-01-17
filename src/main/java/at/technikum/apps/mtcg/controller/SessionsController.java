package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Session;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

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
        return create(request);
      default:
        break;
    }
    return new Response(
      HttpStatus.METHOD_NOT_ALLOWED,
      "This request is not supported"
    );
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
      if (e.getMessage().contains("Invalid username/password provided")) {
        return new Response(
          HttpStatus.UNAUTHORIZED,
          "Invalid username/password provided"
        );
      } else if (e.getMessage().contains("Internal server error")) {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Internal server error"
        );
      } else {
        return new Response(
          HttpStatus.CONFLICT,
          "Session could not be registered"
        );
      }
    }
    if (sessionOptional.isEmpty()) {
      return new Response(
        HttpStatus.CONFLICT,
        "Session could not be registered"
      );
    } else {
      session = sessionOptional.get();
      String taskJson = null;
      try {
        taskJson = objectMapper.writeValueAsString(session);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      return new Response(HttpStatus.OK, taskJson);
    }
  }
}
