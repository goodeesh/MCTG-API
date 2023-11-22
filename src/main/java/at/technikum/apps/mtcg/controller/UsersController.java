package at.technikum.apps.mtcg.controller;

import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class UsersController implements Controller {

    @Override
    public boolean supports(String route) {
        return route.equals("/users");
    }

    @Override
    public Response handle(Request request) {
        String route = request.getRoute();
        String username = "";
        Integer positionOfSecondSlash = route.indexOf("/", 1);
        System.err.println(positionOfSecondSlash);
        if (positionOfSecondSlash != -1) {
            username = route.substring(positionOfSecondSlash + 1, route.length());
            System.err.println(username);

        }
        if (positionOfSecondSlash == -1) { // no username set
            switch (request.getMethod()) {
                case "GET":
                    System.err.println("this is a get request in " + request.getRoute() + "... handling it");
                    break;
                case "POST":
                    System.err.println("this is a post request in " + request.getRoute() + "... handling it");
                    break;
                case "PUT":
                    System.err.println("this is a put request in " + request.getRoute() + "... handling it");
                    break;
                default:
                    break;
            }
        } else { // username was set
            switch (request.getMethod()) {
                case "GET":
                    System.err.println(
                            "this is a GET request in " + request.getRoute() + "... handling it for user " + username);
                    break;
                case "PUT":
                    System.err.println(
                            "this is a PUT request in " + request.getRoute() + "... handling it for user " + username);
                    break;
                default:
                    break;
            }
        }
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(HttpContentType.TEXT_PLAIN);

        response.setBody("hi! from users controller");

        return response;
    }
}
