package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PackagesController implements Controller {

  private final PackageService packageService;

  public PackagesController() {
    this.packageService = new PackageService();
  }

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
    return new Response(
      HttpStatus.METHOD_NOT_ALLOWED,
      "This request is not supported"
    );
  }

  public Response create(Request request) {
    System.err.println("create package: " + request.getAuthorization());
    if (
      !request.getAuthorization().equals("Bearer admin-mtcgToken")
    ) return new Response(HttpStatus.UNAUTHORIZED, "Unauthorized");
    ObjectMapper objectMapper = new ObjectMapper();
    Card[] cards = null;
    try {
      cards = objectMapper.readValue(request.getBody(), Card[].class);
    } catch (JsonProcessingException e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    Package packageToCreate = new Package(cards, "admin");
    try {
      Package createdPackage = packageService.create(packageToCreate);
      return new Response(
        HttpStatus.CREATED,
        objectMapper.writeValueAsString(createdPackage)
      );
    } catch (Exception e) {
      return new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
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
