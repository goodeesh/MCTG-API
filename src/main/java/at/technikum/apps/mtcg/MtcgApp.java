package at.technikum.apps.mtcg;

import at.technikum.apps.mtcg.controller.BattlesController;
import at.technikum.apps.mtcg.controller.CardsController;
import at.technikum.apps.mtcg.controller.Controller;
import at.technikum.apps.mtcg.controller.DeckController;
import at.technikum.apps.mtcg.controller.PackagesController;
import at.technikum.apps.mtcg.controller.ScoreboardController;
import at.technikum.apps.mtcg.controller.SessionsController;
import at.technikum.apps.mtcg.controller.StatsController;
import at.technikum.apps.mtcg.controller.TradingsController;
import at.technikum.apps.mtcg.controller.TransactionsController;
import at.technikum.apps.mtcg.controller.UsersController;
import at.technikum.server.ServerApplication;
import at.technikum.server.http.HttpContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.ArrayList;
import java.util.List;

public class MtcgApp implements ServerApplication {

    private List<Controller> controllers = new ArrayList<>();

    public MtcgApp() {
        controllers.add(new UsersController());
        controllers.add(new SessionsController());
        controllers.add(new PackagesController());
        controllers.add(new TransactionsController());
        controllers.add(new CardsController());
        controllers.add(new DeckController());
        controllers.add(new StatsController());
        controllers.add(new ScoreboardController());
        controllers.add(new BattlesController());
        controllers.add(new TradingsController());
    }

    @Override
    public Response handle(Request request) {
        String route = request.getRoute();
        Integer positionOfSecondSlash = route.indexOf("/", 1);
        String controllerString;
        if (positionOfSecondSlash == -1) {
            controllerString = route;
        } else {
            controllerString = route.substring(0,positionOfSecondSlash);
        }
        System.err.println(controllerString);
        for (Controller controller : controllers) {
            if (!controller.supports(controllerString)) {
                continue;
            }

            return controller.handle(request);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(HttpContentType.TEXT_PLAIN);
        response.setBody("Route " + request.getRoute() + " not found in app!");

        return response;
    }
}
