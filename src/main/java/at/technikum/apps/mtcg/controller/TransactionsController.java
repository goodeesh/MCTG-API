package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.auth.Auth;
import at.technikum.apps.mtcg.service.TransactionService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class TransactionsController implements Controller {

  @Override
  public boolean supports(String route) {
    return route.equals("/transactions");
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
    if (positionOfSecondSlash == -1) { //no valid call set atm for only transactions
      switch (request.getMethod()) {
        default:
          break;
      }
    } else if (
      positionOfSecondSlash != -1 && secondArgument.equals("packages")
    ) { //packgages was set as second part of my route
      switch (request.getMethod()) {
        case "POST":
          return buy(request);
        default:
          System.err.println(("this is default"));
          break;
      }
    }
    Response response = new Response();
    response.setStatus(HttpStatus.OK);
    response.setContentType(HttpContentType.TEXT_PLAIN);
    response.setBody("transanctions controller");

    return response;
  }

  public Response buy(Request request) {
    Auth auth = new Auth();
    String username = auth.extractUsernameFromToken(request.getAuthorization());
    try {
      TransactionService transactionService = new TransactionService();
      transactionService.buy(username);
    } catch (RuntimeException e) {
      if (e.getMessage().contains("Not allowed to do this")) {
        return new Response(HttpStatus.UNAUTHORIZED, "Not allowed to do this");
      } else if (e.getMessage().contains("Package not found")) {
        return new Response(HttpStatus.NOT_FOUND, "Package not found");
      } else if (e.getMessage().contains("Not enough money")) {
        return new Response(HttpStatus.FORBIDDEN, "Not enough money");
      } else {
        return new Response(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Something went wrong"
        );
      }
    }
    return new Response(HttpStatus.OK, "Transaction successful");
  }
}
