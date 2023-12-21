package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Stats;
import at.technikum.apps.mtcg.helper.Helper;
import at.technikum.apps.mtcg.service.StatsService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

public class StatsController implements Controller {

  private StatsService statsService = new StatsService();

  @Override
  public boolean supports(String route) {
    return route.equals("/stats");
  }

  @Override
  public Response handle(Request request) {
    String route = request.getRoute();
    new Helper();
    Optional<String> secondParameter = Helper.getSecondParameterRoute(route);
    if (secondParameter.isEmpty()) { // no username set
      switch (request.getMethod()) {
        case "GET":
          return find(request);
        default:
          break;
      }
    }
    return new Response(
      HttpStatus.METHOD_NOT_ALLOWED,
      "This request is not supported"
    );
  }

  private Response find(Request request) {
    ObjectMapper objectMapper = new ObjectMapper();
    String token = request.getAuthorization();
    Stats stats;
    try {
      stats = statsService.getStatsFromUser(token);
    } catch (RuntimeException e) {
      if (e.getMessage().contains("Not allowed to do this")) {
        return new Response(HttpStatus.UNAUTHORIZED, "Not allowed to do this");
      } else {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
    }
    String statsJson;
    try {
      statsJson = objectMapper.writeValueAsString(stats);
    } catch (JsonProcessingException e) {
      return new Response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong"
      );
    }
    System.err.println(statsJson);
    return new Response(HttpStatus.OK, statsJson);
  }
}
