package at.technikum.apps.mtcg.controller;

import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class TradingsController implements Controller {

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
            secondArgument = route.substring(positionOfSecondSlash+1,route.length());
        }
        if (positionOfSecondSlash == -1) {
            switch (request.getMethod()) {
            case "GET":
                System.err.println("this is a get request in " + request.getRoute() + "... handling it");
                break;
            case "POST":
                System.err.println("this is a post request in " + request.getRoute() + "... handling it");
                break;
            default:
                break;
            }
        } else {
            switch (request.getMethod()) {
                case "DELETE":
                    System.err.println("this is a DELETE request in " + request.getRoute() + "... handling it for deal " + secondArgument);
                    break;
                case "POST":
                    System.err.println("this is a POST request in " + request.getRoute() + "... handling it for deal " + secondArgument);
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
}