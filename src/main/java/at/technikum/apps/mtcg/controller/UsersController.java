package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersController implements Controller {

    private final UserService userService;

    public UsersController() {
        this.userService = new UserService();
    }

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
                    return create(request);
                case "PUT":
                    System.err.println("this is a put request in " + request.getRoute() + "... handling it");
                    break;
                default:
                    break;
            }
        } else { // username was set
            switch (request.getMethod()) {
                case "GET":
                System.err.println(request.getBody());
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

        response.setBody("servus! from users controller");

        return response;
    }
    public Response create(Request request) {
            System.err.println(request.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            User user = null;
            try {
                user = objectMapper.readValue(request.getBody(), User.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            
            // task = toObject(request.getBody(), Task.class);
            System.err.println(user);
            user = userService.save(user);
    
            String taskJson = null;
            try {
                taskJson = objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
    
            Response response = new Response();
            // THOUGHT: better status 201 Created
            response.setStatus(HttpStatus.OK);
            response.setContentType(HttpContentType.APPLICATION_JSON);
            response.setBody(taskJson);
    
            return response;
    
            // return json(task);
        }
}
