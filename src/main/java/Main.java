import app.login.*;
import app.index.*;
import app.tickets.TicketController;
import app.util.*;
import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class Main {

    public static void main(String[] args) {

        // Configure Spark
        port(getHerokuAssignedPort());

        staticFiles.location("/public");

        staticFiles.expireTime(600L);
        enableDebugScreen();

        // Set up before-filters (called before each get/post)
        before("*",                  Filters.addTrailingSlashes);
        //before("*",                  Filters.handleLocaleChange);

        // Set up routes
        redirect.get("/", "/index/");
        get(Path.Web.INDEX,          IndexController.serveIndexPage);
        get(Path.Web.LOGIN,          LoginController.serveLoginPage);
        post(Path.Web.LOGIN,         LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,        LoginController.handleLogoutPost);

        get(Path.Web.CREATE_TICKETS, TicketController.serveCreatePage);
        post(Path.Web.CREATE_TICKETS, TicketController.handleCreateTicketPost);

        get(Path.Web.EDIT_TICKETS + ":id/", TicketController.serveEditPage);
        post(Path.Web.EDIT_TICKETS + ":id/", TicketController.handleEditTicketPost);
        get("*",                     ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);

    }

    static int getHerokuAssignedPort() {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null) {
          return Integer.parseInt(processBuilder.environment().get("PORT"));
      }
      return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }

}

