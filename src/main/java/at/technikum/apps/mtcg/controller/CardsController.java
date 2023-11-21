package at.technikum.apps.mtcg.controller;

import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class CardsController implements Controller {

    @Override
    public boolean supports(String route) {
        return route.equals("/cards");
    }

    @Override
    public Response handle(Request request) {
        switch (request.getMethod()) {
            case "GET":
                System.err.println("this is a get request in " + request.getRoute() + "... handling it");
                break;
            default:
                break;
        }
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.TEXT_PLAIN);
        response.setBody("cards controller");

        return response;
    }
}