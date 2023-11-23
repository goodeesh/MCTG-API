package at.technikum;

import java.io.IOException;

//import at.technikum.apps.display.DisplayApp;
import at.technikum.apps.mtcg.MtcgApp;
import at.technikum.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new MtcgApp());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}