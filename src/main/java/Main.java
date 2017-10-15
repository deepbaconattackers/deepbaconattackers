import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());

        get("/hello", (req, res) -> "<p>(SPIDER) Why hello Meester Ant!</p>\n(ANT) H-hello?\n(SPIDER) It's so nice to see you tooday!\n(ANT) I-it is?\n(SPIDER) Why yes! In fact, I would luv it eef you could join me FOR DEENUR!!! BWAHAHAHAHA!!!\n");
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
