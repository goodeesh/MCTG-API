package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class CardsController implements Controller {

  @Override
  public boolean supports(String route) {
    return route.equals("/cards");
  }

  @Override
  public Response handle(Request request) {
    switch (request.getMethod()) {
      case "GET":
        return getCardsFromUser(request);
      default:
        break;
    }
    Response response = new Response();
    response.setStatus(HttpStatus.OK);
    response.setContentType(HttpContentType.TEXT_PLAIN);
    response.setBody("cards controller");

    return response;
  }

  private final Response getCardsFromUser(Request request) {
    String authToken = request.getAuthorization();
    Auth auth = new Auth();
    String username = auth.extractUsernameFromToken(authToken);
    List<Card> cards = null;
    try {
      CardService cardService = new CardService();
      cards = cardService.getCardsFromUser(username, authToken);
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
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return new Response(
        HttpStatus.OK,
        objectMapper.writeValueAsString(cards)
      );
    } catch (Exception e) {
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
  }
}
