package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Trading;
import at.technikum.apps.mtcg.helper.Helper;
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
          return postTrade(request);
        default:
          break;
      }
    } else {
      switch (request.getMethod()) {
        case "DELETE":
          return deleteTrade(request);
        case "POST":
          return acceptTrade(request);
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

  public Response deleteTrade(Request request) {
    String token = request.getAuthorization();
    String id = Helper.getSecondParameterRoute(request.getRoute()).get();
    try {
      tradingService.delete(id, token);
      return new Response(HttpStatus.OK, "Trading deleted");
    } catch (RuntimeException e) {
      if (e.getMessage().equals("UnauthorizedError")) {
        return new Response(HttpStatus.UNAUTHORIZED, "UnauthorizedError");
      }
      if (e.getMessage().equals("Trading does not exist")) {
        return new Response(HttpStatus.NOT_FOUND, "Trading does not exist");
      }
      if (e.getMessage().equals("Trading does not belong to you")) {
        return new Response(
          HttpStatus.FORBIDDEN,
          "Trading does not belong to you"
        );
      }
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
  }

  public Response postTrade(Request request) {
    String token = request.getAuthorization();
    ObjectMapper objectMapper = new ObjectMapper();
    Trading trading = null;
    try {
      trading = objectMapper.readValue(request.getBody(), Trading.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    System.err.println(
      "the Type of this card is " +
      Helper.getTypeFromCard(trading.getCard().getName())
    );
    try {
      return new Response(
        HttpStatus.OK,
        objectMapper.writeValueAsString(tradingService.save(trading, token))
      );
    } catch (JsonProcessingException e) {
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Error processing JSON"
      );
    } catch (RuntimeException e) {
      if (e.getMessage().equals("UnauthorizedError")) {
        return new Response(HttpStatus.UNAUTHORIZED, "UnauthorizedError");
      }
      if (e.getMessage().equals("Card does not belong to you")) {
        return new Response(
          HttpStatus.FORBIDDEN,
          "Card does not belong to you"
        );
      }
      if (e.getMessage().equals("Card is in deck. Not allowed to trade it")) {
        return new Response(
          HttpStatus.FORBIDDEN,
          "Card is in deck. Not allowed to trade it"
        );
      }
      if (e.getMessage().equals("Trading already exists")) {
        return new Response(HttpStatus.CONFLICT, "Trading already exists");
      }
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
  }

  public Response acceptTrade(Request request) {
    String token = request.getAuthorization();
    String tradingId = Helper.getSecondParameterRoute(request.getRoute()).get();
    String cardId = request.getBody();
    try {
      tradingService.acceptTrade(tradingId, token, cardId);
      return new Response(HttpStatus.OK, "Trading accepted");
    } catch (RuntimeException e) {
      if (e.getMessage().equals("UnauthorizedError")) {
        return new Response(HttpStatus.UNAUTHORIZED, "UnauthorizedError");
      }
      if (e.getMessage().equals("Trading does not exist")) {
        return new Response(HttpStatus.NOT_FOUND, "Trading does not exist");
      }
      if (e.getMessage().equals("Trading does not belong to you")) {
        return new Response(
          HttpStatus.FORBIDDEN,
          "Trading does not belong to you"
        );
      }
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
  }
}
