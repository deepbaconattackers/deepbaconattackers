package app.tickets;

import app.data.TicketDao;
import app.models.Ticket;
import app.user.User;
import app.user.UserDao;
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
    static TicketDao ticketDao = new TicketDao(sql2o);

    public static Route serveCreatePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        model.put("rooms", ticketDao.GetRooms());
        return ViewUtil.render(request, model, Path.Template.CREATE_TICKET);
    };

    public static Route handleCreateTicketPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String name = request.queryParams("name");
        String type = request.queryParams("type");
        String roomId = request.queryParams("room");
        //username lives in the session
        //request.session().attribute("currentUser")

        UserDao userDao = new UserDao(sql2o);
        User u = userDao.getUserByUsername(request.session().attribute("currentUser"));

        Ticket ticket = ticketDao.CreateTicket(
                new Ticket(
                        name,
                        type,
                        "Pending Assignment",
                        u.getUserId(),
                        Integer.parseInt(roomId)
                )
        );

        model.put("ticketId", ticket.getId());

        return ViewUtil.render(request, model, Path.Template.CREATE_TICKET_SUCCESS);
    };

}