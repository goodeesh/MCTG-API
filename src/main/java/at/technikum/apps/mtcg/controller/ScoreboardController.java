package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.ScoreboardService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScoreboardController implements Controller {

  private ScoreboardService scoreboardService = new ScoreboardService();

  @Override
  public boolean supports(String route) {
    return route.equals("/scoreboard");
  }

  @Override
  public Response handle(Request request) {
    switch (request.getMethod()) {
      case "GET":
        return handleGet(request);
      default:
        break;
    }
    Response response = new Response();
    response.setStatus(HttpStatus.OK);
    response.setContentType(HttpContentType.TEXT_PLAIN);
    response.setBody("scoreboard controller");

    return response;
  }

  private Response handleGet(Request request) {
    String token = request.getAuthorization();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return new Response(
        HttpStatus.OK,
        objectMapper.writeValueAsString(
          scoreboardService.displayScoreboard(token)
        )
      );
    } catch (Exception e) {
      if (
        e.getMessage().contains("Not allowed to do this")
      ) return new Response(
        HttpStatus.UNAUTHORIZED,
        e.getMessage()
      ); else return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        e.getMessage()
      );
    }
  }
}
