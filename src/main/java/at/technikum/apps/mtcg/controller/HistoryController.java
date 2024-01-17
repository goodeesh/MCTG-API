package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.HistoryService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HistoryController implements Controller {

  private HistoryService historyService = new HistoryService();

  @Override
  public boolean supports(String route) {
    return route.equals("/history");
  }

  @Override
  public Response handle(Request request) {
    switch (request.getMethod()) {
      case "GET":
        return getHistory(request);
      default:
        break;
    }
    return new Response(
      HttpStatus.METHOD_NOT_ALLOWED,
      "This request is not supported"
    );
  }

  private Response getHistory(Request request) {
    String token = request.getAuthorization();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return new Response(
        HttpStatus.OK,
        objectMapper.writeValueAsString(historyService.getHistory(token))
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
