package at.technikum.apps.mtcg.controller;

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
            secondArgument = route.substring(positionOfSecondSlash+1,route.length());
        }
        if(positionOfSecondSlash == -1) { //no valid call set atm for only transactions
            System.err.println("hi");
            switch (request.getMethod()) {
            default:
                break;
            }
        } else if (positionOfSecondSlash != -1 && secondArgument.equals("packages")) { //packgages was set as second part of my route
            switch (request.getMethod()) {
            case "POST":
                System.err.println("this is a POST request in " + request.getRoute() + "... handling it for " + secondArgument);
                break;
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
}
