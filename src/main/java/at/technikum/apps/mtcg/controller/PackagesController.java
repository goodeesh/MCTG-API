package at.technikum.apps.mtcg.controller;

import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class PackagesController implements Controller {

    @Override
    public boolean supports(String route) {
        return route.equals("/packages");
    }

    @Override
    public Response handle(Request request) {
        switch (request.getMethod()) {
            case "POST":
                System.err.println("this is a post request in " + request.getRoute() + "... handling it");
                break;
            default:
                break;
        }
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.TEXT_PLAIN);
        response.setBody("packages controller");

        return response;
    }
}