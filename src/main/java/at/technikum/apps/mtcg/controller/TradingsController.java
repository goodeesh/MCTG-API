package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.TradingsService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TradingsController implements Controller {

  private final TradingsService tradingService;

  public TradingsController() {
    this.tradingService = new TradingsService();
  }

  @Override
  public boolean supports(String route) {
    return route.equals("/tradings");
  }

  @Override
  public Response handle(Request request) {
    String route = request.getRoute();
    Integer positionOfSecondSlash = route.indexOf("/", 1);
    String secondArgument = "";
    if (positionOfSecondSlash != -1) {
      secondArgument =
        route.substring(positionOfSecondSlash + 1, route.length());
    }
    if (positionOfSecondSlash == -1) {
      switch (request.getMethod()) {
        case "GET":
          return findAll(request);
        case "POST":
          System.err.println(
            "this is a post request in " +
            request.getRoute() +
            "... handling it"
          );
          break;
        default:
          break;
      }
    } else {
      switch (request.getMethod()) {
        case "DELETE":
          System.err.println(
            "this is a DELETE request in " +
            request.getRoute() +
            "... handling it for deal " +
            secondArgument
          );
          break;
        case "POST":
          System.err.println(
            "this is a POST request in " +
            request.getRoute() +
            "... handling it for deal " +
            secondArgument
          );
          break;
        default:
          break;
      }
    }

    Response response = new Response();
    response.setStatus(HttpStatus.OK);
    response.setContentType(HttpContentType.TEXT_PLAIN);
    response.setBody("tradings controller");

    return response;
  }

  public Response findAll(Request request) {
    ObjectMapper objectMapper = new ObjectMapper();
    String tradingsJson = null;
    try {
      tradingsJson = objectMapper.writeValueAsString(tradingService.findAll());
    } catch (JsonProcessingException e) {
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
    return new Response(HttpStatus.OK, tradingsJson);
  }
}
