package app.tickets;

import app.data.TicketDao;
import app.models.Ticket;
import app.util.ConnInfo;
import app.util.Path;
import app.util.ViewUtil;
import org.sql2o.Sql2o;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class TicketController {

    static ConnInfo connInfo = ConnInfo.getConnectionInfo() == null ? new ConnInfo("","","") : ConnInfo.getConnectionInfo();
    static Sql2o sql2o = new Sql2o(connInfo.getDbUrl() + "?sslmode=require",
            connInfo.getUserName(), connInfo.getPassword()
    );

    public static Route serveCreatePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.CREATE_TICKET);
    };

    public static Route handleCreateTicketPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String name = request.queryParams("name");
        String type = request.queryParams("type");

        //username lives in the session
        //request.session().attribute("currentUser")

        //todo: a couple of these things are hardcoded
        TicketDao ticketDao = new TicketDao(sql2o);
        Ticket ticket = ticketDao.CreateTicket(
                new Ticket(
                        name,
                        type,
                        "Pending Assignment",
                        1, //todo: hardcoded -- either add the id to the session on login or look up user by user name
                        1 //todo: hardcoded
                )
        );

        model.put("ticketId", ticket.getId());

        return ViewUtil.render(request, model, Path.Template.CREATE_TICKET_SUCCESS);
    };

}