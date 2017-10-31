package app.data;

import app.index.TicketSummary; //probably should move to models folder or something
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class TicketDao {

    private Sql2o sql2o;

    public TicketDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Iterable<TicketSummary> GetRecentTickets()
    {
        try (Connection c = sql2o.open())
        {
            List<TicketSummary> tickets = c.createQuery("select created, room_name as room, ticket_name as title, ticket_status as status from tickets join rooms on tickets.room_id = rooms.room_id order by created desc limit 10")
                    .executeAndFetch(TicketSummary.class);

            return tickets;
        }
        catch(Exception e)
        {
            //todo: log it or do something
            return null;
        }
    }
}
