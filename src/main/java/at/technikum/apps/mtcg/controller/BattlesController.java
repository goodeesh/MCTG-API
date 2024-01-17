package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.BattleService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class BattlesController implements Controller {

  BattleService battleService = new BattleService();

  @Override
  public boolean supports(String route) {
    return route.equals("/battles");
  }

  @Override
  public Response handle(Request request) {
    switch (request.getMethod()) {
      case "POST":
        return startBattle(request);
      default:
        break;
    }
    return new Response(
      HttpStatus.METHOD_NOT_ALLOWED,
      "This request is not supported"
    );
  }

  private Response startBattle(Request request) {
    String token = request.getAuthorization();
    //ObjectMapper objectMapper = new ObjectMapper();
    String responseString = battleService.startBattle(token);
    return new Response(HttpStatus.OK, responseString);
  }
}
