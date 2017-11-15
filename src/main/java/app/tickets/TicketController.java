package app.tickets;

import app.data.TicketDao;
import app.index.TicketSummary;
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
import app.helpers.AuthenticationHelpers;

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

    public static Route serveEditPage = (Request request, Response response) -> {
        if(!AuthenticationHelpers.isLoggedIn(request))
            response.redirect(Path.Web.LOGIN);

        Map<String, Object> model = new HashMap<>();
        //model.put("tickets", getLatestTicketsWithId());
        //model.put("tickets", getLatestTickets());
        //String my_id = request.queryParams("id");
        //int id_num = Integer.parseInt(my_id);
        model.put("tickets", getTicket(Integer.parseInt(request.params(":id"))));
        model.put("rooms", ticketDao.GetRooms());
        return ViewUtil.render(request, model, Path.Template.EDIT_TICKET);
    };

    public static Route handleEditTicketPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String name = request.queryParams("name");
        String roomId = request.queryParams("room");
        String ticketId = request.queryParams("id");
        String status = request.queryParams("status");
        //username lives in the session
        //request.session().attribute("currentUser")

        UserDao userDao = new UserDao(sql2o);
        User u = userDao.getUserByUsername(request.session().attribute("currentUser"));

        Ticket ticket = ticketDao.EditTicket(
                new Ticket(
                        Integer.parseInt(ticketId),
                        name,
                        status,
                        u.getUserId(),
                        Integer.parseInt(roomId)
                )
        );

        model.put("ticketId", ticket.getId());

        return ViewUtil.render(request, model, Path.Template.EDIT_TICKET_SUCCESS);
    };

    public static Iterable<TicketSummary> getTicket(int id) {
        TicketDao ticketDao = new TicketDao(sql2o);
        return ticketDao.GetTicketById(id);
    }

    public static Iterable<TicketSummary> getLatestTicketsWithId() {
        TicketDao ticketDao = new TicketDao(sql2o);
        return ticketDao.GetTicketsWithId();
    }
}