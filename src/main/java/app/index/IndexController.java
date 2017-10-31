package app.index;

import app.data.TicketDao;
import app.util.*;
import org.sql2o.Sql2o;
import spark.*;
import java.util.*;

public class IndexController {

    static ConnInfo connInfo = ConnInfo.getConnectionInfo() == null ? new ConnInfo("","","") : ConnInfo.getConnectionInfo();
    static Sql2o sql2o = new Sql2o(connInfo.getDbUrl() + "?sslmode=require",
            connInfo.getUserName(), connInfo.getPassword()
    );

    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("tickets", getLatestTickets());
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };
    //todo: find out if there is a way to just call a method from the velocity template instead of putting on the model above
    public static Iterable<TicketSummary> getLatestTickets() {
        TicketDao ticketDao = new TicketDao(sql2o);
        return ticketDao.GetRecentTickets();
    }
}

